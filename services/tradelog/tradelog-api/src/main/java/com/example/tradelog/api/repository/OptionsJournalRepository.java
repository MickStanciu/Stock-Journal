package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public class OptionsJournalRepository {

    private static final Logger log = LoggerFactory.getLogger(OptionsJournalRepository.class);

    private static final String JOURNAL_READ_FIRST_QUERY =
            "SELECT CAST(transaction_id AS VARCHAR(36)), " +
                    "       CAST(transaction_fk AS VARCHAR(36)), " +
                    "       CAST(account_fk AS VARCHAR(36)), " +
                    "       date, " +
                    "       symbol, " +
                    "       stock_price, " +
                    "       strike_price, " +
                    "       expiry_date, " +
                    "       implied_volatility, " +
                    "       implied_volatility_hist, " +
                    "       profit_probability, " +
                    "       contract_number, " +
                    "       premium, " +
                    "       action_fk, " +
                    "       action_type_fk, " +
                    "       broker_fees, " +
                    "       mark " +
                    "FROM simple_option " +
                    "LIMIT 1;";

    private static final String JOURNAL_READ_ALL_FOR_ACCOUNT =
            "SELECT CAST(transaction_id AS VARCHAR(36)), " +
                    "       CAST(transaction_fk AS VARCHAR(36)), " +
                    "       CAST(account_fk AS VARCHAR(36)), " +
                    "       date, " +
                    "       symbol, " +
                    "       stock_price, " +
                    "       strike_price, " +
                    "       expiry_date, " +
                    "       implied_volatility, " +
                    "       implied_volatility_hist, " +
                    "       profit_probability, " +
                    "       contract_number, " +
                    "       premium, " +
                    "       action_fk, " +
                    "       action_type_fk, " +
                    "       broker_fees, " +
                    "       mark " +
                    "FROM simple_option " +
                    "WHERE account_fk = CAST(? AS uuid)" +
                    "ORDER BY expiry_date, symbol ASC;";

    private JdbcTemplate jdbcTemplate;

    public OptionsJournalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkFirstRecord() {
        List<OptionJournalModel> results = jdbcTemplate.query(JOURNAL_READ_FIRST_QUERY, new JournalModelRowMapper());
        return results.size() == 1;
    }

    public List<OptionJournalModel> getAllByAccount(String accountId) {
        Object[] parameters = new Object[] {accountId};
        return jdbcTemplate.query(JOURNAL_READ_ALL_FOR_ACCOUNT, parameters, new JournalModelRowMapper());
    }
}

class JournalModelRowMapper implements RowMapper<OptionJournalModel> {

    @Override
    public OptionJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OptionJournalModel.builder()
                .withTransactionId(rs.getString("transaction_id"))
                .withPairTransactionId(rs.getString("transaction_fk"))
                .withAccountId(rs.getString("account_fk"))
                .withDate(fromTimestamp(rs.getTimestamp("date")))
                .withStockSymbol(rs.getString("symbol"))
                .withStockPrice(rs.getFloat("stock_price"))
                .withStrikePrice(rs.getFloat("strike_price"))
                .withExpiryDate(fromTimestamp(rs.getTimestamp("expiry_date")))
                .withImpliedVolatility(rs.getFloat("implied_volatility"))
                .withHistoricalImpliedVolatility(rs.getFloat("implied_volatility_hist"))
                .withProfitProbability(rs.getFloat("profit_probability"))
                .withContracts(rs.getInt("contract_number"))
                .withPremium(rs.getFloat("premium"))
                .withAction(Action.lookup(rs.getString("action_fk")))
                .withActionType(ActionType.lookup(rs.getString("action_type_fk")))
                .withBrokerFees(rs.getFloat("broker_fees"))
                .withMark(rs.getString("mark"))
                .build();
    }

    private OffsetDateTime fromTimestamp(Timestamp timestamp) {
        //TODO: not sure about ZoneId
        return OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp.getTime()), ZoneId.systemDefault());
    }
}
