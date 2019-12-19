package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConverter;
import com.example.tradelog.api.core.model.OptionJournalModel;
import com.example.tradelog.api.core.model.TradeSummaryModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class OptionsJournalRepository {

    private static final String JOURNAL_READ_FIRST_QUERY =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       tl.transaction_type_fk, " +
                    "       ol.stock_price, " +
                    "       ol.strike_price, " +
                    "       ol.expiry_date, " +
                    "       ol.contract_number, " +
                    "       ol.premium, " +
                    "       ol.action_fk, " +
                    "       ol.option_type_fk, " +
                    "       tl.broker_fees, " +
                    "       tsl.preferred_price, " +
                    "       tsl.group_selected, " +
                    "       tsl.leg_closed " +
                    "FROM transaction_log tl " +
                    "         INNER JOIN option_log ol on tl.id = ol.transaction_fk " +
                    "         INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk " +
                    "LIMIT 1";

    private static final String JOURNAL_READ_BY_SYMBOL_FOR_ACCOUNT =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       tl.transaction_type_fk, " +
                    "       ol.stock_price, " +
                    "       ol.strike_price, " +
                    "       ol.expiry_date, " +
                    "       ol.contract_number, " +
                    "       ol.premium, " +
                    "       ol.action_fk, " +
                    "       ol.option_type_fk, " +
                    "       tl.broker_fees, " +
                    "       tsl.preferred_price, " +
                    "       tsl.group_selected, " +
                    "       tsl.leg_closed " +
                    "FROM transaction_log tl " +
                    "         INNER JOIN option_log ol on tl.id = ol.transaction_fk " +
                    "         INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk " +
                    "WHERE account_fk = CAST(? AS uuid) and symbol = ? " +
                    "ORDER BY date";

    private static final String JOURNAL_READ_BY_TRANSACTION_ID =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       tl.transaction_type_fk, " +
                    "       ol.stock_price, " +
                    "       ol.strike_price, " +
                    "       ol.expiry_date, " +
                    "       ol.contract_number, " +
                    "       ol.premium, " +
                    "       ol.action_fk, " +
                    "       ol.option_type_fk, " +
                    "       tl.broker_fees, " +
                    "       tsl.preferred_price, " +
                    "       tsl.group_selected, " +
                    "       tsl.leg_closed " +
                    "FROM transaction_log tl " +
                    "         INNER JOIN option_log ol on tl.id = ol.transaction_fk " +
                    "         INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk " +
                    "WHERE tl.id = CAST(? AS uuid)";


    private static final String JOURNAL_CREATE_OPTION_FOR_ACCOUNT =
            "INSERT INTO option_log (transaction_fk, stock_price, strike_price, expiry_date, contract_number, premium, action_fk, option_type_fk) " +
                    "VALUES (CAST(? AS uuid), ?, ?, ?, ?, ?, ?, ?)";

    private static final String JOURNAL_DELETE_OPTION = "DELETE FROM option_log WHERE transaction_fk = CAST(? AS uuid) and option_type_fk in ('CALL', 'PUT')";

    private static final String JOURNAL_GET_SUMMARIES =
            "SELECT tl.symbol, ol.action_fk, ol.premium, tl.broker_fees, ol.contract_number, tl.transaction_type_fk " +
                    "FROM transaction_log tl" +
                    "         INNER JOIN option_log ol ON tl.id = ol.transaction_fk " +
                    "WHERE tl.account_fk = CAST(? AS uuid)" +
                    "  AND transaction_type_fk = 'OPTION' " +
                    "ORDER BY tl.symbol;";

    private JdbcTemplate jdbcTemplate;

    public OptionsJournalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkFirstRecord() {
        List<OptionJournalModel> results = jdbcTemplate.query(JOURNAL_READ_FIRST_QUERY, new OptionJournalModelRowMapper());
        return results.size() == 1;
    }

    public List<OptionJournalModel> getAllBySymbol(String accountId, String symbol) {
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


    /**
     * Creates an option record
     * @param id -
     * @param model -
     */
    public void createOptionRecord(String id, OptionJournalModel model) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(JOURNAL_CREATE_OPTION_FOR_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, id);
            ps.setDouble(2, model.getStockPrice());
            ps.setDouble(3, model.getStrikePrice());
            ps.setTimestamp(4, TimeConverter.fromOffsetDateTime(model.getExpiryDate()));
            ps.setInt(5, model.getContracts());
            ps.setDouble(6, model.getPremium());
            ps.setString(7, model.getAction().name());
            ps.setString(8, model.getOptionType().name());
            return ps;
        });
    }


    /**
     * Deletes a share record
     * @param id -
     * @return true/false
     */
    public boolean deleteRecord(String id) {
        Object[] parameters = new Object[] {id};
        return jdbcTemplate.update(JOURNAL_DELETE_OPTION, parameters) == 1;
    }


    /**
     * Get list of option trade summaries
     * @param accountId -
     * @return -
     */
    public List<TradeSummaryModel> getSummaries(String accountId) {
        Object[] parameters = new Object[] {accountId};
        return jdbcTemplate.query(JOURNAL_GET_SUMMARIES, parameters, new TradeSummaryModelRowMapper());
    }
}

