package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Data;

@Data
public class Lock {
    private Long id;
    private OperationType type;
    private Resource resource;
    private Transaction transaction;
}
