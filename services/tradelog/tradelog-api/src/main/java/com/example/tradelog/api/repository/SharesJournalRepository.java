package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.ShareJournalModel;
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

    private static final String JOURNAL_READ_SYMBOLS =
            "SELECT DISTINCT symbol FROM shares_log " +
                    "WHERE account_fk = CAST(? AS uuid) " +
                    "ORDER BY symbol ASC;";

    private static final String JOURNAL_READ_BY_SYMBOL_FOR_ACCOUNT =
            "SELECT CAST(transaction_id AS VARCHAR(36)), " +
                    "       CAST(transaction_id AS VARCHAR(36)), " +
                    "       CAST(account_fk AS VARCHAR(36)), " +
                    "       date, " +
                    "       symbol, " +
                    "       price, " +
                    "       quantity, " +
                    "       action_fk, " +
                    "       action_type_fk, " +
                    "       broker_fees, " +
                    "       mark " +
                    "FROM shares_log " +
                    "WHERE account_fk = CAST(? AS uuid) and symbol = ? " +
                    "ORDER BY date ASC;";

    private static final String JOURNAL_READ_BY_TRANSACTION_ID = "NOT IMPLEMENTED";
    private static final String JOURNAL_CREATE_OPTION_FOR_ACCOUNT = "NOT IMPLEMENTED";

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
     */
    public Optional<String> createShareRecord(ShareJournalModel model) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(JOURNAL_CREATE_OPTION_FOR_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            //TODO: fill up the arguments
            return ps;
        }, keyHolder);

        if (keyHolder.getKeyList().isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(keyHolder.getKeyList().get(0).get("transaction_id").toString());
    }
}
