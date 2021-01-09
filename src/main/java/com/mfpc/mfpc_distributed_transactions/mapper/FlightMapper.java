package com.mfpc.mfpc_distributed_transactions.mapper;

import com.mfpc.mfpc_distributed_transactions.business_model.Airport;
import com.mfpc.mfpc_distributed_transactions.business_model.Flight;
import com.mfpc.mfpc_distributed_transactions.data_model.FlightDb;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface FlightMapper {
    Flight flightDbToFlight(FlightDb flightDb);


    @Mapping(source = "departureAirport", target = "departureAirportId", qualifiedByName = "departureAirportToDepartureAirportId")
    @Mapping(source = "arrivalAirport", target = "arrivalAirportId", qualifiedByName = "arrivalAirportToArrivalAirportId")
    FlightDb flightToFlightDb(Flight flight);

    @Named("departureAirportToDepartureAirportId")
    static Long departureAirportToDepartureAirportId(Airport departureAirport) {
        return departureAirport.getId();
    }

    @Named("arrivalAirportToArrivalAirportId")
    static Long arrivalAirportToArrivalAirportId(Airport arrivalAirport) {
        return arrivalAirport.getId();
    }
}
