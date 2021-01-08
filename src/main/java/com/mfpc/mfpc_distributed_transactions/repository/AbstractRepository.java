package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.data_model.DbRecord;
import com.mfpc.mfpc_distributed_transactions.transaction.model.*;
import com.mfpc.mfpc_distributed_transactions.transaction.service.TransactionScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

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

    public T find(Long id, Transaction transaction) {
        logger.debug("find " + getTableName() + "." + id + " | " + transaction);

        Operation operation = createOperation(OperationType.READ, id, transaction);

        registerOperation(operation);

        logger.debug("find before lock " + getTableName() + "." + id + " | " + transaction);
        TransactionScheduler.lockResource(operation.getResource(), transaction);
        logger.debug("find after lock " + getTableName() + "." + id + " | " + transaction);

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
        String updateQuery = this.createUpdateQuery(t);
        jdbcTemplate.update(updateQuery);
    }

    public void delete(Long id) {
        String query = createDeleteQuery(id);
        jdbcTemplate.update(query);
    }

    protected abstract String getTableName();

    protected abstract List<Pair<String, String>> getObjectAttributes(T t);

    private Class<T> initializeTypeClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Long initializeId() {
        String query = "SELECT MAX(id) FROM " + this.getTableName() + ";";
        logger.debug(query);
        return this.jdbcTemplate.queryForObject(query, Long.class);
    }

    private Operation createOperation(OperationType operationType, Long id, Transaction transaction) {
        Resource resource = Resource
                .builder()
                .tableName(getTableName())
                .recordId(id)
                .build();

        return Operation
                .builder()
                .id(UUID.randomUUID())
                .parent(transaction)
                .type(operationType)
                .resource(resource)
                .compensationQuery("")
                .build();
    }

    private void registerOperation(Operation operation) {
        TransactionScheduler.addOperationToTransaction(operation);
    }

    private String createInsertQuery(T t) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(this.getTableName());
        List<Pair<String, String>> objectAttributes = getObjectAttributes(t);

        query.append(" (id");
        objectAttributes.forEach(pair -> query.append(", ").append(pair.getFirst()));
        query.append(") VALUES (");
        query.append(t.getId());
        objectAttributes.forEach(pair -> query.append(", ").append(wrapWithQuotes(pair.getSecond())));
        query.append(");");

        String queryStr = query.toString();
        logger.debug(queryStr);
        return queryStr;
    }

    private String createSelectQuery(Long id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(this.getTableName());
        if (id != null) {
            query.append(" WHERE id = ");
            query.append(id);
        }
        query.append(";");

        String queryStr = query.toString();
        logger.debug(queryStr);
        return queryStr;
    }

    private String createUpdateQuery(T t) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(this.getTableName());
        query.append(" SET ");
        List<Pair<String, String>> objectAttributes = getObjectAttributes(t);

        query.append(objectAttributes.get(0).getFirst() + " = " + wrapWithQuotes(objectAttributes.get(0).getSecond()));
        objectAttributes.remove(0);
        objectAttributes.forEach(pair -> query.append(", " + pair.getFirst() + " = " + wrapWithQuotes(pair.getSecond())));

        query.append("WHERE id = ");
        query.append(t.getId());
        query.append(";");

        String queryStr = query.toString();
        logger.debug(queryStr);
        return queryStr;
    }

    private String createDeleteQuery(Long id) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ");
        query.append(this.getTableName());
        if (id != null) {
            query.append(" WHERE id = ");
            query.append(id);
        }
        query.append(";");

        String queryStr = query.toString();
        logger.debug(queryStr);
        return queryStr;
    }

    private String wrapWithQuotes(String fieldValue) {
        return "'" + fieldValue + "'";
    }
}
