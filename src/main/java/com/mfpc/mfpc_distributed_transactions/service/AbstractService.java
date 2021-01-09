package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import com.mfpc.mfpc_distributed_transactions.transaction.service.TransactionScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbstractService {
    private Map<UUID, Transaction> transactionsToCommit = new HashMap<>();

    protected Transaction initializeAndRegisterTransactionIfNeeded(Transaction transaction) {
        if (transaction == null) {
            transaction = Transaction.defaultTransaction(currentThread());
            TransactionScheduler.addTransaction(transaction);
            transactionsToCommit.put(transaction.getId(), transaction);
        }

        return transaction;
    }

    protected void commitTransactionIfNeeded(Transaction transaction) {
        Transaction transactionToCommit = transactionsToCommit.get(transaction.getId());
        if (transactionToCommit != null) {
            TransactionScheduler.commitTransaction(transactionToCommit);
        }
    }

    protected Thread currentThread() {
        return Thread.currentThread();
    }
}
