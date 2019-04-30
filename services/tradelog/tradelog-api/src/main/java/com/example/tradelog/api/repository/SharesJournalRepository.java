package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.ShareJournalModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
