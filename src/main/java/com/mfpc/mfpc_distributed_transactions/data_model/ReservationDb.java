package com.mfpc.mfpc_distributed_transactions.data_model;

import lombok.Data;

@Data
public class ReservationDb extends DbRecord {
    private Long flightId;
    private Long userId;
}
