package com.mfpc.mfpc_distributed_transactions.data_model;

import lombok.Data;

@Data
public class AirportDb extends DbRecord {
    private Long cityId;
    private String name;
    private String code;
    private Float latitude;
    private Float longitude;
}
