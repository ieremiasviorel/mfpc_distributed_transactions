package com.mfpc.mfpc_distributed_transactions.business_model;

import com.mfpc.mfpc_distributed_transactions.data_model.DbRecord;
import lombok.Data;

@Data
public class Reservation extends DbRecord {
    private Flight flight;
    private User user;
}
