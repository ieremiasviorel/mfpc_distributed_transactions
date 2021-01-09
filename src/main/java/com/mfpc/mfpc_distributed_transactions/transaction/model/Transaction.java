package com.mfpc.mfpc_distributed_transactions.transaction.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Transaction {
    private UUID id;
    private Timestamp timestamp;
    private TransactionStatus status;
    private List<Operation> operations;
    private Thread thread;

    public static Transaction defaultTransaction(Thread thread) {
        return Transaction
                .builder()
                .id(UUID.randomUUID())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(TransactionStatus.ACTIVE)
                .operations(new ArrayList<>())
                .thread(thread)
                .build();
    }

    @Override
    public String toString() {
        return "TRANSACTION [" +
                "id=" + id +
                ", operations=" + operations.size() +
                ", thread=" + thread.getId() +
                " ]";
    }
}
