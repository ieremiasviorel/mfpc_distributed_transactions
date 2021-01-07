package com.mfpc.mfpc_distributed_transactions.business_model;

import com.mfpc.mfpc_distributed_transactions.data_model.DbRecord;
import lombok.Data;

@Data
public class Airport extends DbRecord {
    private City city;
    private String name;
    private String code;
    private Float latitude;
    private Float longitude;
}
