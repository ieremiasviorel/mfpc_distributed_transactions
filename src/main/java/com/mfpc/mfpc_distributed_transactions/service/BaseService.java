package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;

import java.util.List;

public interface BaseService<T> {
    Long insert(T t);

    T find(Long id, Transaction transaction);

    List<T> findAll();

    void update(T t);

    void delete(Long id);

    default Thread currentThread() {
        return Thread.currentThread();
    }
}
