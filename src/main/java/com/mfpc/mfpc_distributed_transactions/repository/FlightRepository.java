package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.data_model.FlightDb;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository extends AbstractRepository<FlightDb> {
    public FlightRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return "flight";
    }

    @Override
    protected List<Pair<String, String>> getObjectAttributes(FlightDb flightDb) {
        List<Pair<String, String>> attributes = new ArrayList<>();
        attributes.add(Pair.of("flightNumber", flightDb.getFlightNumber()));
        attributes.add(Pair.of("airplaneType", flightDb.getAirplaneType()));
        attributes.add(Pair.of("departureAirportId", flightDb.getDepartureAirportId().toString()));
        attributes.add(Pair.of("arrivalAirportId", flightDb.getArrivalAirportId().toString()));
        attributes.add(Pair.of("departureTime", flightDb.getDepartureTime().toString()));
        attributes.add(Pair.of("arrivalTime", flightDb.getArrivalTime().toString()));

        return attributes;
    }
}
