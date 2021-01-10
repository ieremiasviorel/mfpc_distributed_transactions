package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.data_model.DbRecord;
import com.mfpc.mfpc_distributed_transactions.data_model.ReservationDb;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Operation;
import com.mfpc.mfpc_distributed_transactions.transaction.model.OperationType;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository extends AbstractRepository<ReservationDb> {
    private final Logger logger = LoggerFactory.getLogger(ReservationRepository.class);

    public ReservationRepository(@Autowired @Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<ReservationDb> findByFlightId(Long id, Transaction transaction) {
        logger.debug("GET BY FLIGHT ID " + getTableName() + ".ALL" + " | " + transaction.getId());

        Operation operation = createOperation(OperationType.READ, DbRecord.ALL, transaction);
        registerOperation(operation);
        operation.setCompensationQuery(null);

        String query = createSelectByFlightIdQuery(id);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<ReservationDb>(this.typeClass));
    }

    @Override
    protected String getTableName() {
        return "reservation";
    }

    @Override
    protected List<Pair<String, String>> getObjectAttributes(ReservationDb reservationDb) {
        List<Pair<String, String>> attributes = new ArrayList<>();
        attributes.add(Pair.of("flightId", reservationDb.getFlightId().toString()));
        attributes.add(Pair.of("userId", reservationDb.getUserId().toString()));

        return attributes;
    }

    private String createSelectByFlightIdQuery(Long id) {
        return "SELECT * FROM " + this.getTableName() + " WHERE flightId = " + id + ";";
    }
}
