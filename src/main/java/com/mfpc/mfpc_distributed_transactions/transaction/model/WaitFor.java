package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Data;

import java.util.List;

@Data
public class WaitFor {
    private Lock lock;
    private Transaction hasLock;
    private List<Transaction> waitForLock;
}
