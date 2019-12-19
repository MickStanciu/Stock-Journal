package com.example.tradelog.api.repository;

import com.example.tradelog.api.core.model.ShareJournalModel;
import com.example.tradelog.api.core.model.TradeSummaryModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class JSharesJournalRepository {

    private static final String JOURNAL_READ_BY_SYMBOL_FOR_ACCOUNT =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       tl.transaction_type_fk, " +
                    "       sl.price, " +
                    "       sd.price as current_price, " +
                    "       sl.quantity, " +
                    "       sl.action_fk, " +
                    "       tl.broker_fees, " +
                    "       tsl.preferred_price, " +
                    "       tsl.group_selected, " +
                    "       tsl.leg_closed " +
                    "FROM transaction_log tl " +
                    "         INNER JOIN shares_log sl ON tl.id = sl.transaction_fk " +
                    "         INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk " +
                    "         LEFT JOIN shares_data sd ON sd.symbol = tl.symbol " +
                    "WHERE account_fk = CAST(? AS uuid) " +
                    "  and tl.transaction_type_fk = 'SHARE' " +
                    "  and tl.symbol = ? " +
                    "ORDER BY date;";

    private static final String JOURNAL_READ_BY_TRANSACTION_ID =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       tl.transaction_type_fk, " +
                    "       sl.price, " +
                    "       sd.price as current_price, " +
                    "       sl.quantity, " +
                    "       sl.action_fk, " +
                    "       tl.broker_fees, " +
                    "       tsl.preferred_price, " +
                    "       tsl.group_selected, " +
                    "       tsl.leg_closed " +
                    "FROM transaction_log tl " +
                    "         INNER JOIN shares_log sl ON tl.id = sl.transaction_fk " +
                    "         INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk " +
                    "         LEFT JOIN shares_data sd ON sd.symbol = tl.symbol " +
                    "WHERE tl.id = CAST(? AS uuid);";

    private static final String JOURNAL_CREATE_SHARE_FOR_ACCOUNT =
            "INSERT INTO shares_log (transaction_fk, price, quantity, action_fk) " +
                    "VALUES (CAST(? AS uuid), ?, ?, ?);";

    private static final String JOURNAL_EDIT_SHARE =
            "UPDATE shares_log SET price = ?, quantity = ?, action_fk = ? where transaction_fk = CAST(? AS uuid)";

    private static final String JOURNAL_DELETE_SHARE = "DELETE FROM shares_log WHERE transaction_fk = CAST(? AS uuid)";

    private static final String JOURNAL_GET_SUMMARIES =
            "SELECT tl.symbol, sl.price, tl.broker_fees, sl.quantity, sl.action_fk, tl.transaction_type_fk, sd.price AS current_price " +
                    "FROM transaction_log tl" +
                    "         INNER JOIN shares_log sl ON tl.id = sl.transaction_fk " +
                    "         LEFT JOIN shares_data sd ON sd.symbol = tl.symbol " +
                    "WHERE tl.account_fk = CAST(? AS uuid)" +
                    "  AND tl.transaction_type_fk = 'SHARE' " +
                    "ORDER BY symbol;";

    private JdbcTemplate jdbcTemplate;

    public JSharesJournalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<ShareJournalModel> getAllBySymbol(String accountId, String symbol) {
        Object[] parameters = new Object[] {accountId, symbol};
        return jdbcTemplate.query(JOURNAL_READ_BY_SYMBOL_FOR_ACCOUNT, parameters, new ShareJournalModelRowMapper());
    }

    public Optional<ShareJournalModel> getByTransactionId(String transactionId) {
        Object[] parameters = new Object[] {transactionId};
        List<ShareJournalModel> modelList = jdbcTemplate.query(JOURNAL_READ_BY_TRANSACTION_ID, parameters, new ShareJournalModelRowMapper());
        if (modelList.size() == 1) {
            return Optional.ofNullable(modelList.get(0));
        }
        return Optional.empty();
    }


    /**
     * Creates a share record
     * @param id -
     * @param model -
     */
    public void createShareRecord(String id, ShareJournalModel model) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(JOURNAL_CREATE_SHARE_FOR_ACCOUNT);
            ps.setString(1,id);
            ps.setDouble(2, model.getPrice());
            ps.setInt(3, model.getQuantity());
            ps.setString(4, model.getAction().name());
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
        return jdbcTemplate.update(JOURNAL_DELETE_SHARE, parameters) == 1;
    }


    /**
     * Get list of share trade summaries
     * @param accountId -
     * @return -
     */
    public List<TradeSummaryModel> getSummaries(String accountId) {
        Object[] parameters = new Object[] {accountId};
        return jdbcTemplate.query(JOURNAL_GET_SUMMARIES, parameters, new TradeSummaryModelRowMapper());
    }

    public boolean updateShareRecord(ShareJournalModel model) {
        Object[] parameters = new Object[] { model.getPrice(), model.getQuantity(), model.getAction().toString(), model.getTransactionDetails().getId() };
        return jdbcTemplate.update(JOURNAL_EDIT_SHARE, parameters) == 1;
    }
}
