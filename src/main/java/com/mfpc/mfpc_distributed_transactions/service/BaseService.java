package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;

import java.util.List;

public interface BaseService<T> {
    Long insert(T t, Transaction transaction);

    T find(Long id, Transaction transaction);

    List<T> findAll(Transaction transaction);

    void update(T t, Transaction transaction);

    void delete(Long id, Transaction transaction);
}
