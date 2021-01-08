package com.mfpc.mfpc_distributed_transactions.mapper;

import com.mfpc.mfpc_distributed_transactions.business_model.City;
import com.mfpc.mfpc_distributed_transactions.data_model.CityDb;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {
    City cityDbToCity(CityDb cityDb);
    CityDb cityToCityDb(City city);
}
