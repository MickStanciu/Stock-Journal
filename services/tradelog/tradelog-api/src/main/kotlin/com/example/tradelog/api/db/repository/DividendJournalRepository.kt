package com.example.tradelog.api.db.repository

import com.example.common.error.DataAccessError
import com.example.common.types.Either
import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.DividendModelRowMapper
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Connection

@Service
class DividendJournalRepository(private val jdbcTemplate: JdbcTemplate) : JournalRepository<DividendJournalModel> {

    companion object {
        private const val GET_SUMMARIES = """
            SELECT tl.symbol, dl.dividend, dl.quantity, tl.transaction_type_fk
                FROM transaction_log tl
                 INNER JOIN dividend_log dl ON tl.id = dl.transaction_fk
                 INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk
                WHERE tl.account_fk = CAST(? AS uuid)
                  AND transaction_type_fk = 'DIVIDEND'
                ORDER BY symbol;
        """

        private const val GET_BY_SYMBOL = """
                SELECT CAST(tl.id AS VARCHAR(36)),
                    CAST(tl.account_fk AS VARCHAR(36)),
                    CAST(tl.portfolio_fk AS VARCHAR(36)),
                    tl.date,
                    tl.symbol,
                    tl.broker_fees,
                    tl.transaction_type_fk,
                    dl.dividend,
                    dl.quantity,
                    tsl.preferred_price,
                    tsl.group_selected,
                    tsl.leg_closed 
                FROM transaction_log tl 
                      INNER JOIN dividend_log dl ON tl.id = dl.transaction_fk 
                      INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk 
                WHERE account_fk = CAST(? AS uuid) 
                    AND tl.portfolio_fk = CAST(? AS uuid)
                    AND tl.transaction_type_fk = 'DIVIDEND' 
                    AND symbol = ?
                ORDER BY date;
        """

        private const val GET_BY_ID = """
                    SELECT CAST(tl.id AS uuid),
                        CAST(tl.account_fk AS uuid),
                        CAST(tl.portfolio_fk AS VARCHAR(36)),
                        tl.date,
                        tl.symbol,
                        tl.transaction_type_fk,
                        tl.broker_fees,
                        dl.dividend,
                        dl.quantity,
                        tsl.preferred_price,
                        tsl.group_selected,
                        tsl.leg_closed
                    FROM transaction_log tl
                      INNER JOIN dividend_log dl ON tl.id = dl.transaction_fk
                      INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk
                    WHERE dl.transaction_fk = CAST(? AS uuid)
        """

        private const val CREATE_RECORD = "INSERT INTO dividend_log (transaction_fk, dividend, quantity) VALUES (CAST(? AS uuid), ?, ?)"

        private const val EDIT_RECORD = "UPDATE dividend_log SET dividend = ?, quantity = ? WHERE transaction_fk = CAST(? AS uuid)"

        private const val DELETE_RECORD = "DELETE FROM dividend_log WHERE transaction_fk = CAST(? AS uuid);"
    }

    override fun getSummaries(accountId: String): Either<DataAccessError, List<TradeSummaryModel>> {
        val parameters = arrayOf(accountId)
        return jdbcTemplate.query(GET_SUMMARIES, parameters, TradeSummaryModelRowMapper())
    }


    override fun createRecord(transactionId: String, model: DividendJournalModel) {
        jdbcTemplate.update {connection: Connection ->
            val ps = connection.prepareStatement(CREATE_RECORD)
            ps.setString(1, transactionId)
            ps.setDouble(2, model.dividend)
            ps.setInt(3, model.quantity)
            ps
        }
    }

    override fun getById(accountId: String, transactionId: String): Either<DataAccessError, DividendJournalModel> {
        val parameters = arrayOf(transactionId)
        val models = jdbcTemplate.query(GET_BY_ID, parameters, DividendModelRowMapper())
        if (models.size == 1) {
            return Either.Value(models[0])
        }

        return Either.Error(DataAccessError.RecordNotFound("Couldn't find dividend record with id $transactionId"))
    }

    override fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): List<DividendJournalModel> {
        val parameters = arrayOf(accountId, portfolioId, symbol)
        return jdbcTemplate.query(GET_BY_SYMBOL, parameters, DividendModelRowMapper())
    }

    override fun editRecord(model: DividendJournalModel): Boolean {
        return jdbcTemplate.update { connection: Connection ->
            val ps = connection.prepareStatement(EDIT_RECORD)
            ps.setDouble(1, model.dividend)
            ps.setInt(2, model.quantity)
            ps.setString(3, model.transactionDetails.id)
            ps
        } == 1
    }

    override fun deleteRecord(transactionId: String): Boolean {
        return jdbcTemplate.update { connection: Connection ->
            val ps = connection.prepareStatement(DELETE_RECORD)
            ps.setString(1, transactionId)
            ps
        } == 1
    }
}