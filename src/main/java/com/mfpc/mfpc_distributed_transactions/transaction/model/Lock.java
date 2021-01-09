package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Lock {
    private UUID id;
    private OperationType type;
    // locked resource
    private Resource resource;
    // transaction holding the lock
    private Transaction transaction;

    @Override
    public String toString() {
        return "LOCK [ " +
                "type=" + type +
                ", transaction=" + transaction.getId() +
                " ]";
    }
}
