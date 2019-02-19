package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.JournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JournalRepository {

    private static final Logger log = LoggerFactory.getLogger(JournalRepository.class);

    private static final String JOURNAL_READ_FIRST_QUERY =
            "select CAST(transaction_id as VARCHAR(36)), " +
            "       CAST(account_fk as VARCHAR(36)), " +
            "       date, " +
            "       symbol, " +
            "       mark, " +
            "       stock_price, " +
            "       implied_volatility, " +
            "       implied_volatility_hist, " +
            "       action_fk, " +
            "       action_type_fk, " +
            "       broker_fees " +
            "from tradelog " +
            "limit 1;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JournalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkFirstRecord() {
        List<JournalModel> results = jdbcTemplate.query(JOURNAL_READ_FIRST_QUERY, new JournalModelRowMapper());
        return results.size() == 1;
    }
}

class JournalModelRowMapper implements RowMapper<JournalModel> {

    @Override
    public JournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return JournalModel.builder()
                .withTransactionId(rs.getString("transaction_id"))
                .build();
    }
}
