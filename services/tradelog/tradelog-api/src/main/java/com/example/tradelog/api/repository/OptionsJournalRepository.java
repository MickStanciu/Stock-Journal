package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class OptionsJournalRepository {

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
                    "WHERE account_fk = CAST(? AS uuid) " +
                    "ORDER BY expiry_date, symbol ASC;";

    private static final String JOURNAL_READ_BY_SYMBOL_FOR_ACCOUNT =
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
                    "WHERE account_fk = CAST(? AS uuid) and symbol = ? " +
                    "ORDER BY date ASC;";

    private static final String JOURNAL_READ_BY_TRANSACTION_ID =
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
                    "WHERE transaction_id = CAST(? AS uuid)";

    private static final String JOURNAL_READ_SYMBOLS =
            "SELECT DISTINCT symbol FROM simple_option " +
                    "WHERE account_fk = CAST(? AS uuid) " +
                    "ORDER BY symbol ASC;";

    private static final String JOURNAL_CREATE_OPTION_FOR_ACCOUNT =
            "INSERT INTO simple_option (transaction_fk, account_fk, date, symbol, stock_price, strike_price, " +
                    "expiry_date, implied_volatility, implied_volatility_hist, profit_probability, contract_number, premium, " +
                    "action_fk, action_type_fk, broker_fees, mark) " +
                    "VALUES (null, CAST(? AS uuid), ?, ?, ?, ?, ?, " +
                    "null, null, null, ?, ?, ?, ?, ?, null); ";

    private JdbcTemplate jdbcTemplate;

    public OptionsJournalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkFirstRecord() {
        List<OptionJournalModel> results = jdbcTemplate.query(JOURNAL_READ_FIRST_QUERY, new OptionJournalModelRowMapper());
        return results.size() == 1;
    }

    public List<OptionJournalModel> getAllByAccount(String accountId) {
        Object[] parameters = new Object[] {accountId};
        return jdbcTemplate.query(JOURNAL_READ_ALL_FOR_ACCOUNT, parameters, new OptionJournalModelRowMapper());
    }

    public List<OptionJournalModel> getAllBySymbolAndAccount(String accountId, String symbol) {
        Object[] parameters = new Object[] {accountId, symbol};
        return jdbcTemplate.query(JOURNAL_READ_BY_SYMBOL_FOR_ACCOUNT, parameters, new OptionJournalModelRowMapper());
    }

    public Optional<OptionJournalModel> getByTransactionId(String transactionId) {
        Object[] parameters = new Object[] {transactionId};
        List<OptionJournalModel> modelList = jdbcTemplate.query(JOURNAL_READ_BY_TRANSACTION_ID, parameters, new OptionJournalModelRowMapper());
        if (modelList.size() == 1) {
            return Optional.ofNullable(modelList.get(0));
        }
        return Optional.empty();
    }

    public List<String> getUniqueSymbols(String accountId) {
        Object[] parameters = new Object[] {accountId};
        return jdbcTemplate.query(JOURNAL_READ_SYMBOLS, parameters, new SymbolRowMapper());
    }

    /**
     * Creates an option record
     * @param model -
     * @return Optional of the transaction ID
     */
    public Optional<String> createOptionRecord(OptionJournalModel model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(JOURNAL_CREATE_OPTION_FOR_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, model.getAccountId());
            ps.setTimestamp(2, TimeConversion.fromOffsetDateTime(model.getDate()));
            ps.setString(3, model.getStockSymbol());
            ps.setDouble(4, model.getStockPrice());
            ps.setDouble(5, model.getStrikePrice());
            ps.setTimestamp(6, TimeConversion.fromOffsetDateTime(model.getExpiryDate()));
            ps.setInt(7, model.getContracts());
            ps.setDouble(8, model.getPremium());
            ps.setString(9, model.getAction().name());
            ps.setString(10, model.getActionType().name());
            ps.setDouble(11, model.getBrokerFees());
            return ps;
        }, keyHolder);

        if (keyHolder.getKeyList().isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(keyHolder.getKeyList().get(0).get("transaction_id").toString());
    }
}

