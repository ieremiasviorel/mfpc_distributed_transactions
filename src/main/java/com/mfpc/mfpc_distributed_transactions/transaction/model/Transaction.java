package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Transaction {

    private Long id;
    private Timestamp timestamp;
    private TransactionStatus status;
    private List<Operation> operations;
}
