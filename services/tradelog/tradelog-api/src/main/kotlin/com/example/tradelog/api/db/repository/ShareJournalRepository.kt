package com.example.tradelog.api.db.repository

import com.example.tradelog.api.core.model.ShareJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ShareJournalRepository(private val jdbcTemplate: JdbcTemplate) : JournalRepository<ShareJournalModel> {

    /**
     * Get list of share trade summaries
     * @param accountId -
     * @return -
     */
    override fun getSummaries(accountId: String): List<TradeSummaryModel> {
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

    override fun createRecord(transactionId: String, model: ShareJournalModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(transactionId: String): ShareJournalModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllBySymbol(accountId: String, symbol: String): List<ShareJournalModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editRecord(model: ShareJournalModel): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteRecord(transactionId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
