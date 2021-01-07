package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.model.CityDb;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CityRepository extends AbstractRepository<CityDb> {
    public CityRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return "city";
    }

    @Override
    protected List<Pair<String, String>> getObjectAttributes(CityDb cityDb) {
        List<Pair<String, String>> attributes = new ArrayList<>();
        attributes.add(Pair.of("name", cityDb.getName()));
        attributes.add(Pair.of("country", cityDb.getCountry()));

        return attributes;
    }
}
