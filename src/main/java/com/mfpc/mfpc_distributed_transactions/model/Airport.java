package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;

@Data
public class Airport extends DbRecord {
    private City city;
    private Float latitude;
    private Float longitude;
}
