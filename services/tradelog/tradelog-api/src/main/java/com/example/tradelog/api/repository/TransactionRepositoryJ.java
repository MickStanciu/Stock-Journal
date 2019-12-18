package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConverter;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class TransactionRepositoryJ {

    private static final Logger log = LoggerFactory.getLogger(TransactionRepositoryJ.class);

    private static final String JOURNAL_CREATE_TRANSACTION_FOR_ACCOUNT =
            "INSERT INTO transaction_log (account_fk, date, symbol, transaction_type_fk, broker_fees) " +
                    "VALUES (CAST(? AS uuid), ?, ?, ?, ?)";

    private static final String JOURNAL_DELETE_TRANSACTION_FOR_ACCOUNT =
            "DELETE FROM transaction_log WHERE id = CAST(? AS uuid) and account_fk = CAST(? AS uuid) " +
                    "and transaction_type_fk = ?";

    private static final String JOURNAL_EDIT_TRANSACTION_FOR_ACCOUNT =
            "UPDATE transaction_log SET broker_fees = ?, date = ? where id = CAST(? AS uuid) and account_fk = CAST(? AS uuid)";

    private static final String JOURNAL_CREATE_SETTINGS =
            "INSERT INTO transaction_settings_log (transaction_fk, preferred_price, group_selected, leg_closed) VALUES (CAST(? AS uuid), ?, ?, ?)";

    private static final String JOURNAL_UPDATE_SETTINGS =
            "UPDATE transaction_settings_log SET preferred_price = ?, group_selected = ?, leg_closed = ? WHERE transaction_fk = CAST(? AS uuid)";

    private static final String JOURNAL_DELETE_SETTINGS =
            "DELETE FROM transaction_settings_log WHERE transaction_fk = CAST(? AS uuid)";

    private JdbcTemplate jdbcTemplate;

    public TransactionRepositoryJ(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Creates a transaction record
     * @param model -
     * @return Optional of the transaction ID
     */
    public Optional<String> createTransactionRecord(TransactionModel model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(JOURNAL_CREATE_TRANSACTION_FOR_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, model.getAccountId());
            ps.setTimestamp(2, TimeConverter.fromOffsetDateTime(model.getDate()));
            ps.setString(3, model.getSymbol());
            ps.setString(4, model.getType().name());
            ps.setDouble(5, model.getBrokerFees());
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

        return Optional.of(key);
    }

    public boolean updateTransactionRecord(TransactionModel model) {
        Object[] parameters = new Object[] {
                model.getBrokerFees(), model.getDate(), model.getId(), model.getAccountId()
        };
        return jdbcTemplate.update(JOURNAL_EDIT_TRANSACTION_FOR_ACCOUNT, parameters) == 1;
    }

    /**
     * Deletes a share record
     * @param id -
     * @param accountId -
     * @return true/false
     */
    public boolean deleteShareRecord(String id, String accountId) {
        Object[] parameters = new Object[] {id, accountId, TransactionType.SHARE.name()};
        return jdbcTemplate.update(JOURNAL_DELETE_TRANSACTION_FOR_ACCOUNT, parameters) == 1;
    }

    public boolean deleteOptionRecord(String id, String accountId) {
        Object[] parameters = new Object[] {id, accountId, TransactionType.OPTION.name()};
        return jdbcTemplate.update(JOURNAL_DELETE_TRANSACTION_FOR_ACCOUNT, parameters) == 1;
    }

    public boolean deleteDividendRecord(String id, String accountId) {
        Object[] parameters = new Object[] {id, accountId, TransactionType.DIVIDEND.name()};
        return jdbcTemplate.update(JOURNAL_DELETE_TRANSACTION_FOR_ACCOUNT, parameters) == 1;
    }

    public boolean updateSettings(TransactionSettingsModel model) {
        Object[] parameters = new Object[] {model.getPreferredPrice(), model.getGroupSelected(), model.getLegClosed(), model.getTransactionId()};
        return jdbcTemplate.update(JOURNAL_UPDATE_SETTINGS, parameters) == 1;
    }

    public boolean createSettings(String transactionId, TransactionSettingsModel model) {
        Object[] parameters = new Object[] {transactionId, model.getPreferredPrice(), model.getGroupSelected(), model.getLegClosed()};
        return jdbcTemplate.update(JOURNAL_CREATE_SETTINGS, parameters) == 1;
    }

    public boolean deleteSettingsRecord(String transactionId) {
        Object[] parameters = new Object[] {transactionId};
        return jdbcTemplate.update(JOURNAL_DELETE_SETTINGS, parameters) == 1;
    }
}
