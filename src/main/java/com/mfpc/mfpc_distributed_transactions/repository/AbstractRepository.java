package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.data_model.DbRecord;
import com.mfpc.mfpc_distributed_transactions.exception.DeadlockException;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Operation;
import com.mfpc.mfpc_distributed_transactions.transaction.model.OperationType;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Resource;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import com.mfpc.mfpc_distributed_transactions.transaction.service.TransactionScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractRepository<T extends DbRecord> {
    private final Logger logger = LoggerFactory.getLogger(AbstractRepository.class);

    protected final JdbcTemplate jdbcTemplate;
    protected final Class<T> typeClass;
    protected Long nextId;

    public AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.typeClass = initializeTypeClass();
        this.nextId = initializeId();
    }

    public Long insert(T t, Transaction transaction) {
        logger.debug("INSERT " + getTableName() + " | " + transaction.getId());

        Operation operation = createOperation(OperationType.WRITE, DbRecord.ALL, transaction);
        registerOperation(operation);
        operation.setCompensationQuery(createDeleteQuery(nextId));

        t.setId(this.nextId);
        String insertQuery = this.createInsertQuery(t);
        jdbcTemplate.update(insertQuery);
        return this.nextId++;
    }

    public T find(Long id, Transaction transaction) {
        logger.debug("GET " + getTableName() + "." + id + " | " + transaction.getId());

        Operation operation = createOperation(OperationType.READ, id, transaction);
        registerOperation(operation);
        operation.setCompensationQuery(null);

        String query = createSelectQuery(id);
        List<T> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<T>(this.typeClass));
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public List<T> findAll(Transaction transaction) {
        logger.debug("GET ALL " + getTableName() + ".ALL" + " | " + transaction.getId());

        Operation operation = createOperation(OperationType.READ, DbRecord.ALL, transaction);
        registerOperation(operation);
        operation.setCompensationQuery(null);

        String query = createSelectQuery(null);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<T>(this.typeClass));
    }

    public void update(T t, Transaction transaction) {
        logger.debug("UPDATE " + getTableName() + "." + t.getId() + " | " + transaction.getId());

        Operation operation = createOperation(OperationType.WRITE, t.getId(), transaction);
        registerOperation(operation);
        T tOld = find(t.getId(), transaction);
        operation.setCompensationQuery(createUpdateQuery(tOld));

        String updateQuery = this.createUpdateQuery(t);
        jdbcTemplate.update(updateQuery);
    }

    public void delete(Long id, Transaction transaction) {
        logger.debug("DELETE " + getTableName() + "." + id + " | " + transaction.getId());

        Operation operation = createOperation(OperationType.WRITE, id, transaction);
        registerOperation(operation);
        T tOld = find(id, transaction);
        operation.setCompensationQuery(createInsertQuery(tOld));

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

        Long id = this.jdbcTemplate.queryForObject(query, Long.class);
        if (id == null) {
            id = 0L;
        }

        return id + 1;
    }

    protected Operation createOperation(OperationType operationType, Long id, Transaction transaction) {
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
                .build();
    }

    protected void registerOperation(Operation operation) {
        try {
            boolean registerOperation = TransactionScheduler.addOperationToTransaction(operation);
            if (!registerOperation) {
                Thread.currentThread().suspend();
            }
        } catch (DeadlockException ex) {
            rollbackTransaction(operation.getParent());
            TransactionScheduler.rollbackTransaction(operation.getParent());
            throw ex;
        }
    }

    private void rollbackTransaction(Transaction transaction) {
        List<Operation> operations = transaction.getOperations()
                .subList(0, transaction.getOperations().size() - 1);
        Collections.reverse(operations);

        logger.debug("ROLLBACK TRANSACTION: " + transaction.getId() + ", " + operations.size() + " operations");

        List<String> compensationQueries = operations
                .stream()
                .map(Operation::getCompensationQuery)
                .collect(Collectors.toList());

        for (String query : compensationQueries) {
            if (query != null) {
                jdbcTemplate.execute(query);
            }
        }
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

        return query.toString();
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

        return query.toString();
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

        return query.toString();
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

        return query.toString();
    }

    private String wrapWithQuotes(String fieldValue) {
        return "'" + fieldValue + "'";
    }
}
