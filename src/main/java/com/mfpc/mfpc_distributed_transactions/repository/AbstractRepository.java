package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.model.DbRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractRepository<T extends DbRecord> {
    private final Logger logger = LoggerFactory.getLogger(AbstractRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final Class<T> typeClass;
    protected Long nextId;

    public AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.typeClass = initializeTypeClass();
        this.nextId = initializeId();
        if (this.nextId == null) {
            this.nextId = 0L;
        }
        this.nextId += 1;
    }

    public Long insert(T t) {
        t.setId(this.nextId);
        String insertQuery = this.createInsertQuery(t);
        jdbcTemplate.update(insertQuery);
        return this.nextId++;
    }

    public T find(Long id) {
        String query = createSelectQuery(id);
        List<T> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<T>(this.typeClass));
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public List<T> findAll() {
        String query = createSelectQuery(null);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<T>(this.typeClass));
    }

    public void update(T t) {
        String updateQuery = this.createInsertQuery(t);
        jdbcTemplate.update(updateQuery);
    }

    public void delete(Long id) {
        String query = createDeleteQuery(id);
        jdbcTemplate.update(query);
    }

    protected abstract String getTableName();

    protected abstract String createInsertQuery(T t);

    protected abstract String createUpdateQuery(T t);

    protected String createSelectQuery(Long id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(this.getTableName());
        if (id != null) {
            query.append(" WHERE id = ");
            query.append(id);
        }

        String queryStr = query.toString();
        logger.debug(queryStr);
        return queryStr;
    }

    private String createDeleteQuery(Long id) {
        StringBuilder query = new StringBuilder();
        query.append(this.getTableName());
        if (id != null) {
            query.append(" WHERE id = ");
            query.append(id);
        }

        String queryStr = query.toString();
        logger.debug(queryStr);
        return queryStr;
    }

    private Class<T> initializeTypeClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Long initializeId() {
        String query = "SELECT MAX(id) FROM " + this.getTableName() + ";";
        logger.debug(query);
        return this.jdbcTemplate.queryForObject(query, Long.class);
    }
}
