package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.model.AirportDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AirportRepository extends AbstractRepository<AirportDb> {
    private final Logger logger = LoggerFactory.getLogger(AbstractRepository.class);

    public AirportRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return "airport";
    }

    @Override
    protected String createInsertQuery(AirportDb airportDb) {
        return null;
    }

    @Override
    protected String createUpdateQuery(AirportDb airportDb) {
        return null;
    }
}
