package com.mfpc.mfpc_distributed_transactions.mapper;

import com.mfpc.mfpc_distributed_transactions.business_model.Airport;
import com.mfpc.mfpc_distributed_transactions.data_model.AirportDb;
import org.mapstruct.Mapper;

@Mapper
public interface AirportMapper {
    Airport airportDbToAirport(AirportDb airportDb);

    AirportDb airportToAirportDb(Airport airport);
}
