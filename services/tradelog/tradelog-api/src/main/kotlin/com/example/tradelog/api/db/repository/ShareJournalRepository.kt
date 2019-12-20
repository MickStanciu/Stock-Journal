package com.example.tradelog.api.db.repository

import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ShareJournalRepository(private val jdbcTemplate: JdbcTemplate) {

    /**
     * Get list of share trade summaries
     * @param accountId -
     * @return -
     */
    fun getSummaries(accountId: String): List<TradeSummaryModel> {
        val parameters = arrayOf(accountId)
        return jdbcTemplate.query(JOURNAL_GET_SUMMARIES, parameters, TradeSummaryModelRowMapper())
    }

    companion object {
        private const val JOURNAL_GET_SUMMARIES =
                "SELECT tl.symbol, sl.price, tl.broker_fees, sl.quantity, sl.action_fk, tl.transaction_type_fk, sd.price AS current_price " +
                        "FROM transaction_log tl" +
                        "         INNER JOIN shares_log sl ON tl.id = sl.transaction_fk " +
                        "         LEFT JOIN shares_data sd ON sd.symbol = tl.symbol " +
                        "WHERE tl.account_fk = CAST(? AS uuid)" +
                        "  AND tl.transaction_type_fk = 'SHARE' " +
                        "ORDER BY symbol;"
    }
}
