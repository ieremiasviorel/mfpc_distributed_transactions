package com.mfpc.mfpc_distributed_transactions.repository;

import com.mfpc.mfpc_distributed_transactions.data_model.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository extends AbstractRepository<UserDb> {
    public UserRepository(@Autowired @Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return "user";
    }

    @Override
    protected List<Pair<String, String>> getObjectAttributes(UserDb userDb) {
        List<Pair<String, String>> attributes = new ArrayList<>();
        attributes.add(Pair.of("username", userDb.getUsername()));
        attributes.add(Pair.of("firstName", userDb.getFirstName()));
        attributes.add(Pair.of("lastName", userDb.getLastName()));

        return attributes;
    }
}
