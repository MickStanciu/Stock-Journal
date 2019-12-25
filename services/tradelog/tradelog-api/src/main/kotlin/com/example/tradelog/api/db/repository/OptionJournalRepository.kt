package com.example.tradelog.api.db.repository

import com.example.tradelog.api.core.model.OptionJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class OptionJournalRepository(private val jdbcTemplate: JdbcTemplate) : JournalRepository<OptionJournalModel> {

    /**
     * Get list of option summaries
     * @param accountId -
     * @return -
     */
    override fun getSummaries(accountId: String): List<TradeSummaryModel> {
        val parameters = arrayOf(accountId)
        return jdbcTemplate.query(JOURNAL_GET_SUMMARIES, parameters, TradeSummaryModelRowMapper())
    }

    companion object {
        private const val JOURNAL_GET_SUMMARIES = "SELECT tl.symbol, ol.action_fk, ol.premium, tl.broker_fees, ol.contract_number, tl.transaction_type_fk " +
                "FROM transaction_log tl" +
                "         INNER JOIN option_log ol ON tl.id = ol.transaction_fk " +
                "WHERE tl.account_fk = CAST(? AS uuid)" +
                "  AND transaction_type_fk = 'OPTION' " +
                "ORDER BY tl.symbol;"
    }

    override fun createRecord(transactionId: String, model: OptionJournalModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(accountId: String, transactionId: String): OptionJournalModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllBySymbol(accountId: String, symbol: String): List<OptionJournalModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editRecord(model: OptionJournalModel): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteRecord(transactionId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}