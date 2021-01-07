package com.mfpc.mfpc_distributed_transactions.business_model;

import com.mfpc.mfpc_distributed_transactions.data_model.DbRecord;
import lombok.Data;

@Data
public class User extends DbRecord {
    private String username;
    private String firstName;
    private String lastName;
}