package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class WaitFor {
    private UUID id;
    private Lock lock;
    // transaction that has the lock
    private Transaction hasLock;
    // transactions that wait for the lock
    private List<Transaction> waitForLock;
}
