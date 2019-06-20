package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class SharesJournalRepository {

    private static final Logger log = LoggerFactory.getLogger(SharesJournalRepository.class);

    private static final String JOURNAL_READ_SYMBOLS =
            "SELECT DISTINCT symbol " +
                    "FROM transaction_log " +
                    "WHERE transaction_type_fk = 'SHARE' AND account_fk = CAST(? AS uuid)" +
                    "ORDER BY symbol";

    private static final String JOURNAL_READ_BY_SYMBOL_FOR_ACCOUNT =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       sl.price, " +
                    "       sl.quantity, " +
                    "       sl.action_fk, " +
                    "       sl.action_type_fk, " +
                    "       sl.broker_fees " +
                    "FROM transaction_log tl " +
                    "         inner join shares_log sl on tl.id = sl.transaction_fk " +
                    "WHERE account_fk = CAST(? AS uuid) " +
                    "  and tl.transaction_type_fk = 'SHARE' " +
                    "  and symbol = ? " +
                    "ORDER BY date;";

    private static final String JOURNAL_READ_BY_TRANSACTION_ID =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       sl.price, " +
                    "       sl.quantity, " +
                    "       sl.action_fk, " +
                    "       sl.action_type_fk, " +
                    "       sl.broker_fees " +
                    "FROM transaction_log tl " +
                    "         inner join shares_log sl on tl.id = sl.transaction_fk " +
                    "WHERE tl.id = CAST(? AS uuid);";

    private static final String JOURNAL_CREATE_SHARE_FOR_ACCOUNT_1 =
            "INSERT INTO transaction_log (account_fk, date, symbol, transaction_type_fk) " +
                    "VALUES (CAST(? AS uuid), ?, ?, 'SHARE')";

    private static final String JOURNAL_CREATE_SHARE_FOR_ACCOUNT_2 =
            "INSERT INTO shares_log (transaction_fk, price, quantity, action_fk, action_type_fk, broker_fees) " +
                    "VALUES (CAST(? AS uuid), ?, ?, ?, ?, ?);";

    private JdbcTemplate jdbcTemplate;

    public SharesJournalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getUniqueSymbols(String accountId) {
        Object[] parameters = new Object[] {accountId};
        return jdbcTemplate.query(JOURNAL_READ_SYMBOLS, parameters, new SymbolRowMapper());
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
     * @param model -
     * @return Optional of the transaction ID
     * TODO: add transactional
     */
    public Optional<String> createShareRecord(ShareJournalModel model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(JOURNAL_CREATE_SHARE_FOR_ACCOUNT_1, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, model.getAccountId());
            ps.setTimestamp(2, TimeConversion.fromOffsetDateTime(model.getDate()));
            ps.setString(3, model.getSymbol());
            return ps;
        }, keyHolder);

        if (keyHolder.getKeyList().isEmpty()) {
            log.error("FAILED TO INSERT TRANSACTION");
            return Optional.empty();
        }

        String key = keyHolder.getKeyList().get(0).get("id").toString();
        if (key == null || key.trim().length() == 0) {
            log.error("FAILED TO INSERT TRANSACTION");
            return Optional.empty();
        }

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(JOURNAL_CREATE_SHARE_FOR_ACCOUNT_2);
            ps.setString(1, key);
            ps.setDouble(2, model.getPrice());
            ps.setInt(3, model.getQuantity());
            ps.setString(4, model.getAction().name());
            ps.setString(5, model.getActionType().name());
            ps.setDouble(6, model.getBrokerFees());
            return ps;
        });

        return Optional.of(key);
    }
}
