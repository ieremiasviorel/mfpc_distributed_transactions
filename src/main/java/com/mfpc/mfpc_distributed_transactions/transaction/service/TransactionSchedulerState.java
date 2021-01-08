package com.mfpc.mfpc_distributed_transactions.transaction.service;

import com.mfpc.mfpc_distributed_transactions.transaction.model.Lock;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import com.mfpc.mfpc_distributed_transactions.transaction.model.WaitFor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionSchedulerState {
    private final List<Transaction> transactions = new ArrayList<>();
    private final List<Lock> locks = new ArrayList<>();
    private final List<WaitFor> waitForGraph = new ArrayList<>();
}
