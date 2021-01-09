package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.data_model.DbRecord;
import com.mfpc.mfpc_distributed_transactions.repository.AbstractRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import com.mfpc.mfpc_distributed_transactions.transaction.service.TransactionScheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractService<T extends DbRecord, TDb extends DbRecord> {
    private final AbstractRepository<TDb> repository;

    private final Map<UUID, Transaction> transactionsToCommit = new HashMap<>();

    public AbstractService(AbstractRepository<TDb> repository) {
        this.repository = repository;
    }

    public Long insert(T t, Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        Long id = repository.insert(tToTDb(t), transaction);

        commitTransactionIfNeeded(transaction);

        return id;
    }

    public T find(Long id, Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        TDb tDb = repository.find(id, transaction);

        T t;
        if (tDb != null) {
            t = tDbToT(tDb, transaction);
        } else {
            t = null;
        }

        commitTransactionIfNeeded(transaction);

        return t;
    }

    public List<T> findAll(Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        Transaction finalTransaction = transaction;
        List<T> ts = repository.findAll(transaction)
                .stream()
                .map(tDb -> this.tDbToT(tDb, finalTransaction))
                .collect(Collectors.toList());

        commitTransactionIfNeeded(transaction);

        return ts;
    }

    public void update(T t, Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        repository.update(tToTDb(t), transaction);

        commitTransactionIfNeeded(transaction);
    }

    public void delete(Long id, Transaction transaction) {
        transaction = initializeAndRegisterTransactionIfNeeded(transaction);

        repository.delete(id, transaction);

        commitTransactionIfNeeded(transaction);
    }

    protected abstract TDb tToTDb(T t);

    protected abstract T tDbToT(TDb tDb, Transaction transaction);

    private Transaction initializeAndRegisterTransactionIfNeeded(Transaction transaction) {
        if (transaction == null) {
            transaction = Transaction.defaultTransaction(currentThread());
            TransactionScheduler.addTransaction(transaction);
            transactionsToCommit.put(transaction.getId(), transaction);
        }

        return transaction;
    }

    private void commitTransactionIfNeeded(Transaction transaction) {
        Transaction transactionToCommit = transactionsToCommit.get(transaction.getId());
        if (transactionToCommit != null) {
            TransactionScheduler.commitTransaction(transactionToCommit);
        }
    }

    private Thread currentThread() {
        return Thread.currentThread();
    }
}
