package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.data_model.ReservationDb;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository extends AbstractRepository<ReservationDb> {
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
}
