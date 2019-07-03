package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DividendRepository {

    private static final String DIVIDEND_READ_BY_SYMBOL_FOR_ACCOUNT =
            "SELECT CAST(tl.id AS VARCHAR(36)), " +
                    "       CAST(tl.account_fk AS VARCHAR(36)), " +
                    "       tl.date, " +
                    "       tl.symbol, " +
                    "       tl.transaction_type_fk, " +
                    "       dl.dividend, " +
                    "       dl.quantity " +
                    "FROM transaction_log tl " +
                    "         inner join dividend_log dl on tl.id = dl.transaction_fk " +
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
}
