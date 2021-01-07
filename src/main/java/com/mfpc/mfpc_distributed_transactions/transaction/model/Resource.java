package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Data;

@Data
public class Resource {
    private String table;
    private Long recordId;
}
