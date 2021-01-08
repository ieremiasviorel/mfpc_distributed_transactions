package com.mfpc.mfpc_distributed_transactions.transaction.service;

import com.mfpc.mfpc_distributed_transactions.transaction.model.Operation;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Resource;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import com.mfpc.mfpc_distributed_transactions.transaction.model.WaitFor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionScheduler extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TransactionScheduler.class);

    private static final TransactionSchedulerState state = new TransactionSchedulerState();

    public static synchronized void addTransaction(Transaction transaction) {
        logger.debug(Thread.currentThread().getId() + " addTransaction " + transaction);

        state.getTransactions().add(transaction);
    }

    public static synchronized void addOperationToTransaction(Operation operation, UUID transactionId) {
        logger.debug(Thread.currentThread().getId() + " addOperation " + operation);

        Optional<Transaction> transactionOptional = state.getTransactions()
                .stream()
                .filter(t -> t.getId().equals(transactionId))
                .findFirst();

        if (transactionOptional.isPresent()) {
            transactionOptional.get().getOperations().add(operation);
        } else {
            logger.warn("No transaction with id " + transactionId.toString() + " found!");
        }
    }

    public static synchronized void lockResource(Resource resource, Transaction transaction) {
        logger.debug(Thread.currentThread().getId() + " lockResource " + resource + " | " + transaction);

        List<Transaction> waitForLock = new ArrayList<>();
        waitForLock.add(transaction);

        WaitFor waitFor = WaitFor
                .builder()
                .id(UUID.randomUUID())
                .hasLock(null)
                .waitForLock(waitForLock)
                .build();

        state.getWaitForGraph().add(waitFor);
        transaction.getThread().suspend();
    }

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
