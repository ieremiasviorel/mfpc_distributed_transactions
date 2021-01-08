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
    private Transaction hasLock;
    private List<Transaction> waitForLock;
}
