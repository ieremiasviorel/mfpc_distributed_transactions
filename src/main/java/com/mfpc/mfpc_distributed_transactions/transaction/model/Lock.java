package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Lock {
    private UUID id;
    private OperationType type;
    private Resource resource;
}
