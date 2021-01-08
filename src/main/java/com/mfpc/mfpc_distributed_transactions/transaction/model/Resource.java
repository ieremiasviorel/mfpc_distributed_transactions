package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resource {
    private String tableName;
    private Long recordId;
}
