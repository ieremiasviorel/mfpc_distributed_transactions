package com.mfpc.mfpc_distributed_transactions.service;

import java.util.List;

public interface BaseService<T> {
    Long insert(T t);

    T find(Long id);

    List<T> findAll();

    void update(T t);

    void delete(Long id);
}
