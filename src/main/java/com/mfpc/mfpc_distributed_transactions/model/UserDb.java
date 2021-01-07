package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;

@Data
public class UserDb extends DbRecord {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}
