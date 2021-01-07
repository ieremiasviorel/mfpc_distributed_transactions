package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.model.CityDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepository extends AbstractRepository<CityDb> {
    private final Logger logger = LoggerFactory.getLogger(AbstractRepository.class);

    public CityRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return "city";
    }

    @Override
    protected String createInsertQuery(CityDb cityDb) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(this.getTableName());
        query.append(" (id, name, country) ");
        query.append("VALUES (");
        query.append(wrapWithQuotes(cityDb.getId().toString()) + ", ");
        query.append(wrapWithQuotes(cityDb.getName()) + ", ");
        query.append(wrapWithQuotes(cityDb.getCountry()) + ");");

        String queryStr = query.toString();
        logger.debug(queryStr);
       return queryStr;
    }

    @Override
    protected String createUpdateQuery(CityDb cityDb) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE " + this.getTableName() + " SET ");
        query.append("name = ?, ");
        query.append("country = ?, ");

        query.append("WHERE id = ?;");

        String queryStr = query.toString();
        logger.debug(queryStr);
        return queryStr;
    }

    private String wrapWithQuotes(String fieldValue) {
        return "'" + fieldValue + "'";
    }
}
