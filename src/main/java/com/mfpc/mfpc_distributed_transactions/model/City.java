package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;

@Data
public class City extends DbRecord {
    private String name;
    private String country;
}
