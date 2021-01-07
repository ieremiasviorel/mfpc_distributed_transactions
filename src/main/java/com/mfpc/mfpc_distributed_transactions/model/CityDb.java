package com.mfpc.mfpc_distributed_transactions.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityDb extends DbRecord {
    private String name;
    private String country;
}
