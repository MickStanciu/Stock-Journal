package com.example.tradelog.api.db.repository

import com.example.tradelog.api.core.model.ShareJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.ShareJournalModelRowMapper
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Connection

@Service
class ShareJournalRepository(private val jdbcTemplate: JdbcTemplate) : JournalRepository<ShareJournalModel> {

    override fun getSummaries(accountId: String): List<TradeSummaryModel> {
        val parameters = arrayOf(accountId)
        return jdbcTemplate.query(GET_SUMMARIES, parameters, TradeSummaryModelRowMapper())
    }

    companion object {
        private const val GET_SUMMARIES = """
            SELECT tl.symbol, sl.price, tl.broker_fees, sl.quantity, sl.action_fk, tl.transaction_type_fk, sd.price AS current_price
                        FROM transaction_log tl
                                 INNER JOIN shares_log sl ON tl.id = sl.transaction_fk
                                 LEFT JOIN shares_data sd ON sd.symbol = tl.symbol
                        WHERE tl.account_fk = CAST(? AS uuid)
                          AND tl.transaction_type_fk = 'SHARE'
                        ORDER BY symbol;
        """

        private const val GET_BY_SYMBOL = """
            SELECT CAST(tl.id AS VARCHAR(36)),
                               CAST(tl.account_fk AS VARCHAR(36)),
                               tl.date,
                               tl.symbol,
                               tl.transaction_type_fk,
                               sl.price,
                               sd.price as current_price,
                               sl.quantity,
                               sl.action_fk,
                               tl.broker_fees,
                               tsl.preferred_price,
                               tsl.group_selected,
                               tsl.leg_closed
                        FROM transaction_log tl
                                 INNER JOIN shares_log sl ON tl.id = sl.transaction_fk
                                 INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk
                                 LEFT JOIN shares_data sd ON sd.symbol = tl.symbol
                        WHERE account_fk = CAST(? AS uuid)
                          and tl.transaction_type_fk = 'SHARE'
                          and tl.symbol = ?
                        ORDER BY date;
        """

        private const val GET_BY_ID = """
            SELECT CAST(tl.id AS VARCHAR(36)),
                           CAST(tl.account_fk AS VARCHAR(36)),
                           tl.date,
                           tl.symbol,
                           tl.transaction_type_fk,
                           sl.price,
                           sd.price as current_price,
                           sl.quantity,
                           sl.action_fk,
                           tl.broker_fees,
                           tsl.preferred_price,
                           tsl.group_selected,
                           tsl.leg_closed
                    FROM transaction_log tl
                             INNER JOIN shares_log sl ON tl.id = sl.transaction_fk
                             INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk
                             LEFT JOIN shares_data sd ON sd.symbol = tl.symbol
                    WHERE tl.account_fk = CAST(? as uuid) AND tl.id = CAST(? AS uuid);
        """

        private const val CREATE_RECORD = "INSERT INTO shares_log (transaction_fk, price, quantity, action_fk) VALUES (CAST(? AS uuid), ?, ?, ?);"

        private const val DELETE_RECORD = "DELETE FROM shares_log WHERE transaction_fk = CAST(? AS uuid)"

        private const val EDIT_RECORD = "UPDATE shares_log SET price = ?, quantity = ?, action_fk = ? where transaction_fk = CAST(? AS uuid)"

    }

    override fun createRecord(transactionId: String, model: ShareJournalModel) {
        jdbcTemplate.update { connection: Connection ->
            val ps = connection.prepareStatement(CREATE_RECORD)
            ps.setString(1, transactionId)
            ps.setDouble(2, model.price)
            ps.setInt(3, model.quantity)
            ps.setString(4, model.action.name)
            ps
        }
    }

    override fun getById(accountId: String, transactionId: String): ShareJournalModel? {
        val parameters = arrayOf<Any>(accountId, transactionId)
        val models = jdbcTemplate.query(GET_BY_ID, parameters, ShareJournalModelRowMapper())
        if (models.size == 1) {
            return models[0]
        }
        return null
    }

    override fun getAllBySymbol(accountId: String, symbol: String): List<ShareJournalModel> {
        val parameters = arrayOf<Any>(accountId, symbol)
        return jdbcTemplate.query(GET_BY_SYMBOL, parameters, ShareJournalModelRowMapper())
    }

    override fun editRecord(model: ShareJournalModel): Boolean {
        val parameters = arrayOf(model.price, model.quantity, model.action.name, model.transactionDetails.id)
        return jdbcTemplate.update(EDIT_RECORD, parameters) == 1
    }

    override fun deleteRecord(transactionId: String): Boolean {
        val parameters = arrayOf<Any>(transactionId)
        return jdbcTemplate.update(DELETE_RECORD, parameters) == 1
    }
}
