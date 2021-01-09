package com.mfpc.mfpc_distributed_transactions.transaction.service;

import com.mfpc.mfpc_distributed_transactions.transaction.model.*;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransactionScheduler extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TransactionScheduler.class);

    private static final TransactionSchedulerState state = new TransactionSchedulerState();

    /**
     * ========================================
     * Accessed by repositories statically
     * ========================================
     */

    public static synchronized void addTransaction(Transaction transaction) {
        logger.debug(Thread.currentThread().getId() + " addTransaction " + transaction);

        state.getTransactions().add(transaction);
    }

    public static synchronized void addOperationToTransaction(Operation operation) {
        logger.debug(Thread.currentThread().getId() + " addOperation " + operation);

        Optional<Transaction> transactionOptional = state.getTransactions()
                .stream()
                .filter(t -> t.getId().equals(operation.getParent().getId()))
                .findFirst();

        if (transactionOptional.isEmpty()) {
            logger.error("No transaction with id " + operation.getParent().getId().toString() + " found!");
            return;
        }

        transactionOptional.get().getOperations().add(operation);
        lockResource(operation);
    }

    public static synchronized void lockResource(Operation operation) {
        logger.debug(Thread.currentThread().getId() + " lockResource " + operation.getResource() + " | " + operation.getParent());

        List<Lock> currentResourceLocks = getCurrentResourceLocks(operation.getResource());
        boolean areLocksCompatibleWithOperation = areLocksCompatibleWithOperation(currentResourceLocks, operation.getType());

        if (areLocksCompatibleWithOperation) {
            createAndRegisterLock(operation);
        } else {
            suspendOperation(operation, currentResourceLocks);
        }
    }

    public static synchronized void commitTransaction(Transaction transaction) {
        List<Lock> locks = state.getLocks()
                .stream()
                .filter(lock -> lock.getTransaction().getId().equals(transaction.getId()))
                .collect(Collectors.toList());

        state.getLocks().removeAll(locks);

        for (Lock lock : locks) {
            Optional<WaitFor> lockWaitFor = state.getWaitForGraph()
                    .stream()
                    .filter(waitFor -> waitFor.getLock().getId().equals(lock.getId()))
                    .findFirst();

            if (lockWaitFor.isPresent()) {
                Transaction transactionToUnlock = lockWaitFor.get().getWaitForLock().get(0);
                transactionToUnlock.getThread().resume();

                if (lockWaitFor.get().getWaitForLock().size() > 1) {
                    lockWaitFor.get().getWaitForLock().remove(0);
                } else {
                    state.getWaitForGraph().remove(lockWaitFor.get());
                }
            }
        }
    }

    private static synchronized List<Lock> getCurrentResourceLocks(Resource resource) {
        return state.getLocks()
                .stream()
                .filter(lock -> lock.getResource().equals(resource))
                .collect(Collectors.toList());
    }

    private static boolean areLocksCompatibleWithOperation(List<Lock> resourceLocks, OperationType type) {
        // no locks currently on the resource
        if (resourceLocks.size() == 0) {
            return true;
        }

        // write operation is incompatible with any existing locks
        if (type.equals(OperationType.WRITE)) {
            return false;
            // read operation is compatible only with existing read locks
        } else {
            return resourceLocks
                    .stream()
                    .allMatch(lock -> lock.getType().equals(OperationType.READ));
        }
    }

    private static synchronized void createAndRegisterLock(Operation operation) {
        Lock lock = Lock
                .builder()
                .id(UUID.randomUUID())
                .type(operation.getType())
                .resource(operation.getResource())
                .transaction(operation.getParent())
                .build();

        state.getLocks().add(lock);
    }

    private static void suspendOperation(Operation operation, List<Lock> currentResourceLocks) {
        List<Pair<Lock, Optional<WaitFor>>> currentWaitFor = getWaitForListForLocks(currentResourceLocks);

        for (Pair<Lock, Optional<WaitFor>> lockWaitFor : currentWaitFor) {
            Lock lock = lockWaitFor.getFirst();
            Optional<WaitFor> waitFor = lockWaitFor.getSecond();

            if (waitFor.isPresent()) {
                waitFor.get().getWaitForLock().add(operation.getParent());
            } else {
                createAndRegisterWaitFor(lock, operation.getParent());
            }
        }
    }

    private static synchronized List<Pair<Lock, Optional<WaitFor>>> getWaitForListForLocks(List<Lock> currentResourceLocks) {
        return currentResourceLocks
                .stream()
                .map(lock ->
                        Pair.of(
                                lock,
                                state.getWaitForGraph()
                                        .stream()
                                        .filter(waitFor -> waitFor.getLock().equals(lock))
                                        .findFirst()
                        )
                )
                .collect(Collectors.toList());
    }

    private static synchronized void createAndRegisterWaitFor(Lock lock, Transaction waitingTransaction) {
        List<Transaction> waitingTransactions = new ArrayList<>();
        waitingTransactions.add(waitingTransaction);

        WaitFor waitFor = WaitFor
                .builder()
                .id(UUID.randomUUID())
                .lock(lock)
                .hasLock(lock.getTransaction())
                .waitForLock(waitingTransactions)
                .build();

        state.getWaitForGraph().add(waitFor);
    }

    /**
     * ========================================
     * Running on own thread
     * ========================================
     */

    @SneakyThrows
    @Override
    public synchronized void run() {
        while (true) {
            Thread.sleep(2000);

            synchronized (state) {
                if (state.getWaitForGraph().size() > 0) {
                    logger.debug("has waiting locks: " + state.getWaitForGraph().size());
                    state.getWaitForGraph().forEach(waitFor -> {
                        if (waitFor.getWaitForLock().size() > 0) {
                            logger.debug("has waiting transactions on lock: " + waitFor.getWaitForLock().size());
                            Transaction transaction = waitFor.getWaitForLock().get(0);
                            logger.debug(Thread.currentThread().getId() + " unlock waiting thread " + transaction.getThread().getId());
                            transaction.getThread().resume();
                            waitFor.getWaitForLock().remove(transaction);
                        }
                    });
                } else {
                    logger.debug("no waiting");
                }
            }
        }
    }
}
