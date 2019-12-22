package com.example.tradelog.api.db.repository

import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class DividendJournalRepository(private val jdbcTemplate: JdbcTemplate) : JournalRepository<DividendJournalModel> {

    /**
     * Get list of dividend summaries
     * @param accountId -
     * @return -
     */
    override fun getSummaries(accountId: String): List<TradeSummaryModel> {
        val parameters = arrayOf(accountId)
        return jdbcTemplate.query(JOURNAL_GET_SUMMARIES, parameters, TradeSummaryModelRowMapper())
    }

    companion object {
        private const val JOURNAL_GET_SUMMARIES = "SELECT tl.symbol, dl.dividend, dl.quantity, tl.transaction_type_fk " +
                "FROM transaction_log tl" +
                "         INNER JOIN dividend_log dl ON tl.id = dl.transaction_fk " +
                "WHERE tl.account_fk = CAST(? AS uuid)" +
                "  AND transaction_type_fk = 'DIVIDEND' " +
                "ORDER BY symbol;"
    }

    override fun createRecord(transactionId: String, model: DividendJournalModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(transactionId: String): DividendJournalModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllBySymbol(accountId: String, symbol: String): List<DividendJournalModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editRecord(model: DividendJournalModel): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteRecord(transactionId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}