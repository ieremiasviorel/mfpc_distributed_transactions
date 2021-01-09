package com.mfpc.mfpc_distributed_transactions.mapper;

import com.mfpc.mfpc_distributed_transactions.business_model.Airport;
import com.mfpc.mfpc_distributed_transactions.business_model.City;
import com.mfpc.mfpc_distributed_transactions.data_model.AirportDb;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface AirportMapper {
    Airport airportDbToAirport(AirportDb airportDb);

    @Mapping(source = "city", target = "cityId", qualifiedByName = "cityToCityId")
    AirportDb airportToAirportDb(Airport airport);

    @Named("cityToCityId")
    static Long cityToCityId(City city) {
        return city.getId();
    }
}
