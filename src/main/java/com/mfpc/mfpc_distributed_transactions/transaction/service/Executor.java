package com.mfpc.mfpc_distributed_transactions.transaction.service;

import com.mfpc.mfpc_distributed_transactions.transaction.model.Lock;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import com.mfpc.mfpc_distributed_transactions.transaction.model.WaitFor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Executor {

    private final List<Transaction> transactions;
    private final List<Lock> locks;
    private final List<WaitFor> waitForGraph;

    public Executor() {
        this.transactions = new ArrayList<Transaction>();
        this.locks = new ArrayList<Lock>();
        this.waitForGraph = new ArrayList<WaitFor>();
    }
}
