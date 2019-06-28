package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionType;
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
public class TransactionRepository {

    private static final Logger log = LoggerFactory.getLogger(TransactionRepository.class);

    private static final String JOURNAL_READ_SYMBOLS =
            "SELECT DISTINCT symbol " +
                    "FROM transaction_log " +
                    "WHERE account_fk = CAST(? AS uuid)" +
                    "ORDER BY symbol ASC";

    private static final String JOURNAL_CREATE_TRANSACTION_FOR_ACCOUNT =
            "INSERT INTO transaction_log (account_fk, date, symbol, transaction_type_fk) " +
                    "VALUES (CAST(? AS uuid), ?, ?, ?)";

    private static final String JOURNAL_DELETE_TRANSACTION_FOR_ACCOUNT =
            "DELETE FROM transaction_log WHERE id = CAST(? AS uuid) and account_fk = CAST(? AS uuid) " +
                    "and symbol = ? and transaction_type_fk = ?";

    private JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getUniqueSymbols(String accountId) {
        Object[] parameters = new Object[] {accountId};
        return jdbcTemplate.query(JOURNAL_READ_SYMBOLS, parameters, new SymbolRowMapper());
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
            ps.setTimestamp(2, TimeConversion.fromOffsetDateTime(model.getDate()));
            ps.setString(3, model.getSymbol());
            ps.setString(4, model.getType().name());
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

    /**
     * Deletes a share record
     * @param id -
     * @param accountId -
     * @param symbol -
     * @return true/false
     */
    public boolean deleteRecord(String id, String accountId, String symbol) {
        Object[] parameters = new Object[] {id, accountId, symbol, TransactionType.SHARE.name()};
        return jdbcTemplate.update(JOURNAL_DELETE_TRANSACTION_FOR_ACCOUNT, parameters) == 1;
    }
}
