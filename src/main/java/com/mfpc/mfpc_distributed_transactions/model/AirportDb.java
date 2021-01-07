package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;

@Data
public class AirportDb extends DbRecord {
    private Long cityId;
    private Float latitude;
    private Float longitude;
}
