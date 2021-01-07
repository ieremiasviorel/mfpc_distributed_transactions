package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Data;

@Data
public class Operation {
    private Long id;
    private Transaction parent;
    private OperationType type;
    private Resource resource;
    private String compensationQuery;
}
