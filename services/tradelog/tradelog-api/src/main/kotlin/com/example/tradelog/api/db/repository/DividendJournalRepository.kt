package com.example.tradelog.api.db.repository

import com.example.common.repository.DataAccessError
import com.example.common.types.Either
import com.example.common.types.Either.Companion.bind
import com.example.common.types.Either.Left
import com.example.common.types.Either.Right
import com.example.common.utils.performSafeCall
import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.DividendModelRowMapper
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Connection
import java.util.*

@Service
class DividendJournalRepository(private val jdbcTemplate: JdbcTemplate) : JournalRepository<DividendJournalModel> {

    companion object {
        private const val GET_SUMMARIES = """
            SELECT tl.symbol, dl.dividend, dl.quantity, tl.transaction_type_fk
                FROM transaction_log tl
                 INNER JOIN dividend_log dl ON tl.id = dl.transaction_fk
                 INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk
                WHERE tl.account_fk = ?
                  AND transaction_type_fk = 'DIVIDEND'
                ORDER BY symbol;
        """

        private const val GET_BY_SYMBOL = """
                SELECT tl.id,
                    tl.account_fk,
                    tl.portfolio_fk,
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
                WHERE account_fk = ? 
                    AND tl.portfolio_fk = ?
                    AND tl.transaction_type_fk = 'DIVIDEND' 
                    AND symbol = ?
                ORDER BY date;
        """

        private const val GET_BY_ID = """
                    SELECT tl.id,
                        tl.account_fk,
                        tl.portfolio_fk,
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
                    WHERE dl.transaction_fk = ?
        """

        private const val CREATE_RECORD = "INSERT INTO dividend_log (transaction_fk, dividend, quantity) VALUES (?, ?, ?)"

        private const val EDIT_RECORD = "UPDATE dividend_log SET dividend = ?, quantity = ? WHERE transaction_fk = ?"

        private const val DELETE_RECORD = "DELETE FROM dividend_log WHERE transaction_fk = ?;"
    }

    override fun getSummaries(accountId: UUID): Either<DataAccessError, List<TradeSummaryModel>> {
        val parameters = arrayOf(accountId)
        return performSafeCall(
                { jdbcTemplate.query(GET_SUMMARIES, parameters, TradeSummaryModelRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }

    override fun createRecord(transactionId: UUID, model: DividendJournalModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(CREATE_RECORD)
                        ps.setObject(1, transactionId, java.sql.Types.OTHER)
                        ps.setDouble(2, model.dividend)
                        ps.setInt(3, model.quantity)
                        ps
                    } == 1
                },
                { DataAccessError.DatabaseAccessError() }
        )

        val checkResponse: (Boolean) -> Either<DataAccessError, Unit> = {
            when (it) {
                true -> Right(Unit)
                false -> Left(DataAccessError.DatabaseAccessError())
            }
        }

        return dbResponse
                .bind(checkResponse)
    }

    override fun getById(accountId: UUID, transactionId: UUID): Either<DataAccessError, DividendJournalModel> {
        val parameters = arrayOf(transactionId)

        val dbResponse: Either<DataAccessError, List<DividendJournalModel>> = performSafeCall(
                { jdbcTemplate.query(GET_BY_ID, parameters, DividendModelRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )

        val checkSize: (List<DividendJournalModel>) -> Either<DataAccessError, DividendJournalModel> = {
            when (it.size == 1) {
                true -> Right(it[0])
                false -> Left(DataAccessError.RecordNotFound("Couldn't find dividend record with id $transactionId"))
            }
        }

        return dbResponse
                .bind(checkSize)
    }

    override fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): Either<DataAccessError, List<DividendJournalModel>> {
        val parameters = arrayOf(accountId, portfolioId, symbol)
        return performSafeCall(
                { jdbcTemplate.query(GET_BY_SYMBOL, parameters, DividendModelRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }

    override fun editRecord(model: DividendJournalModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(EDIT_RECORD)
                        ps.setDouble(1, model.dividend)
                        ps.setInt(2, model.quantity)
                        ps.setObject(3, model.transactionDetails.id, java.sql.Types.OTHER)
                        ps
                    } == 1
                },
                { DataAccessError.DatabaseAccessError() }
        )

        val checkResponse: (Boolean) -> Either<DataAccessError, Unit> = {
            when (it) {
                true -> Right(Unit)
                false -> Left(DataAccessError.DatabaseAccessError())
            }
        }

        return dbResponse
                .bind(checkResponse)
    }

    override fun deleteRecord(transactionId: UUID): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(DELETE_RECORD)
                        ps.setObject(1, transactionId, java.sql.Types.OTHER)
                        ps
                    } == 1
                },
                { DataAccessError.DatabaseAccessError() }
        )

        val checkResponse: (Boolean) -> Either<DataAccessError, Unit> = {
            when (it) {
                true -> Right(Unit)
                false -> Left(DataAccessError.DatabaseAccessError())
            }
        }

        return dbResponse
                .bind(checkResponse)
    }
}