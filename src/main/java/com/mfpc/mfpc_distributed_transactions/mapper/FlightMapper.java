package com.mfpc.mfpc_distributed_transactions.mapper;

import com.mfpc.mfpc_distributed_transactions.business_model.Flight;
import com.mfpc.mfpc_distributed_transactions.data_model.FlightDb;
import org.mapstruct.Mapper;

@Mapper
public interface FlightMapper {
    Flight flightDbToFlight(FlightDb flightDb);

    FlightDb flightToFlightDb(Flight flight);
}
