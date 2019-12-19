package com.example.tradelog.api.repository;

import com.example.tradelog.api.core.model.DividendJournalModel;
import com.example.tradelog.api.core.model.TradeSummaryModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class DividendRepository {

    private static final String DIVIDEND_READ_BY_SYMBOL_FOR_ACCOUNT =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       tl.broker_fees, " +
                    "       tl.transaction_type_fk, " +
                    "       dl.dividend, " +
                    "       dl.quantity, " +
                    "       tsl.preferred_price, " +
                    "       tsl.group_selected, " +
                    "       tsl.leg_closed " +
                    "FROM transaction_log tl " +
                    "         INNER JOIN dividend_log dl ON tl.id = dl.transaction_fk " +
                    "         INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk " +
                    "WHERE account_fk = CAST(? AS uuid) " +
                    "  and tl.transaction_type_fk = 'DIVIDEND' " +
                    "  and symbol = ? " +
                    "ORDER BY date;";

    private static final String JOURNAL_GET_SUMMARIES =
            "SELECT tl.symbol, dl.dividend, dl.quantity, tl.transaction_type_fk " +
                    "FROM transaction_log tl" +
                    "         INNER JOIN dividend_log dl ON tl.id = dl.transaction_fk " +
                    "WHERE tl.account_fk = CAST(? AS uuid)" +
                    "  AND transaction_type_fk = 'DIVIDEND' " +
                    "ORDER BY symbol;";

    private static final String CREATE_RECORD =
            "INSERT INTO dividend_log (transaction_fk, dividend, quantity) VALUES (CAST(? AS uuid), ?, ?)";

    private static final String GET_RECORD_BY_ID =
            "SELECT CAST(tl.id AS uuid), " +
                    "       CAST(tl.account_fk AS uuid), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       tl.transaction_type_fk, " +
                    "       tl.broker_fees, " +
                    "       dl.dividend, " +
                    "       dl.quantity, " +
                    "       tsl.preferred_price, " +
                    "       tsl.group_selected, " +
                    "       tsl.leg_closed " +
                    "FROM transaction_log tl " +
                    "         INNER JOIN dividend_log dl ON tl.id = dl.transaction_fk " +
                    "         INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk " +
                    "WHERE dl.transaction_fk = CAST(? AS uuid)";

    private static final String DELETE_RECORD_BY_ID = "DELETE FROM dividend_log WHERE transaction_fk = CAST(? AS uuid)";

    private JdbcTemplate jdbcTemplate;

    public DividendRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DividendJournalModel> getAllBySymbol(String accountId, String symbol) {
        Object[] parameters = new Object[] {accountId, symbol};
        return jdbcTemplate.query(DIVIDEND_READ_BY_SYMBOL_FOR_ACCOUNT, parameters, new DividendModelRowMapper());
    }


    /**
     * Get list of dividend summaries
     * @param accountId -
     * @return -
     */
    public List<TradeSummaryModel> getSummaries(String accountId) {
        Object[] parameters = new Object[] {accountId};
        return jdbcTemplate.query(JOURNAL_GET_SUMMARIES, parameters, new TradeSummaryModelRowMapper());
    }

    public void createRecord(String transactionId, DividendJournalModel model) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_RECORD);
            ps.setString(1, transactionId);
            ps.setDouble(2, model.getDividend());
            ps.setInt(3, model.getQuantity());
            return ps;
        });
    }

    public Optional<DividendJournalModel> getByTransactionId(String transactionId) {
        Object[] parameters = new Object[] {transactionId};
        List<DividendJournalModel> modelList = jdbcTemplate.query(GET_RECORD_BY_ID, parameters, new DividendModelRowMapper());
        if (modelList.size() == 1) {
            return Optional.ofNullable(modelList.get(0));
        }
        return Optional.empty();
    }

    /**
     * Deletes a dividend record
     * @param transactionId -
     * @return true/false
     */
    public boolean deleteRecord(String transactionId) {
        Object[] parameters = new Object[] {transactionId};
        return jdbcTemplate.update(DELETE_RECORD_BY_ID, parameters) == 1;
    }
}
