package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;

@Data
public class Reservation extends DbRecord {
    private Flight flight;
    private User user;
}
