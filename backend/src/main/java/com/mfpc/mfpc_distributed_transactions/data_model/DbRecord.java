package com.mfpc.mfpc_distributed_transactions.data_model;

import lombok.Data;

@Data
public abstract class DbRecord {
    protected Long id;

    public static final Long ALL = 0L;
}
