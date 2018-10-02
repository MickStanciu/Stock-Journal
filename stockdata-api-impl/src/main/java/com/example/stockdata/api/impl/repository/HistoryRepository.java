package com.example.stockdata.api.impl.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryRepository {

    private static final Logger log = LoggerFactory.getLogger(HistoryRepository.class);

    private static final String ACCOUNT_READ_FIRST_QUERY = "SELECT a.id FROM accounts a " +
            "INNER JOIN account_roles ar ON a.role_fk = ar.id AND CAST(ar.tenant_fk AS uuid) = CAST(a.tenant_fk AS uuid) " +
            "LIMIT 1";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkFirstRecord() {
        List list = jdbcTemplate.queryForList(ACCOUNT_READ_FIRST_QUERY);
        return list.size() == 1;
    }
}

//class AccountModelRowMapper implements RowMapper<AccountModel> {
//
//    @Override
//    public AccountModel mapRow(ResultSet resultSet, int i) throws SQLException {
//        return null;
//    }
//}
