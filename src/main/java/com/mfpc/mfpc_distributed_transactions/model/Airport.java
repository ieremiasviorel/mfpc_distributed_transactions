package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;

@Data
public class Airport extends DbRecord {
    private City city;
    private String name;
    private String code;
    private Float latitude;
    private Float longitude;
}
