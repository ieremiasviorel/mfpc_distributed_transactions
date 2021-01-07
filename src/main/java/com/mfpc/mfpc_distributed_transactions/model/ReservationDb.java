package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;

@Data
public class ReservationDb extends DbRecord {
    private Long flightId;
    private Long userId;
}
