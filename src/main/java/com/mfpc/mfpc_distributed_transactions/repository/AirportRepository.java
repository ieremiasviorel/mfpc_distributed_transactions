package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.model.AirportDb;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AirportRepository extends AbstractRepository<AirportDb> {
    public AirportRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return "airport";
    }

    @Override
    protected List<Pair<String, String>> getObjectAttributes(AirportDb airportDb) {
        List<Pair<String, String>> attributes = new ArrayList<>();
        attributes.add(Pair.of("cityId", airportDb.getCityId().toString()));
        attributes.add(Pair.of("name", airportDb.getName()));
        attributes.add(Pair.of("code", airportDb.getCode()));
        attributes.add(Pair.of("latitude", airportDb.getLatitude().toString()));
        attributes.add(Pair.of("longitude", airportDb.getLongitude().toString()));

        return attributes;
    }
}