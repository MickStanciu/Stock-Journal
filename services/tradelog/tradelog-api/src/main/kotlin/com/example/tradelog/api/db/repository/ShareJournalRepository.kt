package com.example.tradelog.api.db.repository

import com.example.common.repository.DataAccessError
import com.example.common.types.Either
import com.example.common.types.Either.Companion.bind
import com.example.common.types.Either.Left
import com.example.common.types.Either.Right
import com.example.common.utils.performSafeCall
import com.example.tradelog.api.core.model.ShareJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.ShareJournalModelRowMapper
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Connection
import java.util.*

@Service
class ShareJournalRepository(private val jdbcTemplate: JdbcTemplate) : JournalRepository<ShareJournalModel> {

    companion object {
        private const val GET_SUMMARIES = """
            SELECT tl.symbol,
                   sl.price,
                   tl.broker_fees,
                   sl.quantity,
                   sl.action_fk,
                   tl.transaction_type_fk,
                   tsl.leg_closed
            FROM transaction_log tl
                     INNER JOIN shares_log sl ON tl.id = sl.transaction_fk
                     INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk
            WHERE tl.account_fk = ?
                  AND tl.transaction_type_fk = 'SHARE'
            ORDER BY symbol;
        """

        private const val GET_BY_SYMBOL = """
            SELECT tl.id,
                   tl.account_fk,
                   tl.portfolio_fk,
                   tl.date,
                   tl.symbol,
                   tl.transaction_type_fk,
                   sl.price,
                   sl.quantity,
                   sl.action_fk,
                   tl.broker_fees,
                   tsl.preferred_price,
                   tsl.group_selected,
                   tsl.leg_closed
            FROM transaction_log tl
                     INNER JOIN shares_log sl ON tl.id = sl.transaction_fk
                     INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk
            WHERE account_fk = ?
                AND tl.portfolio_fk = ?
                AND tl.transaction_type_fk = 'SHARE'
                AND tl.symbol = ?
            ORDER BY date;
        """

        private const val GET_BY_ID = """
            SELECT tl.id,
                   tl.account_fk,
                   tl.portfolio_fk,
                   tl.date,
                   tl.symbol,
                   tl.transaction_type_fk,
                   sl.price,
                   sl.quantity,
                   sl.action_fk,
                   tl.broker_fees,
                   tsl.preferred_price,
                   tsl.group_selected,
                   tsl.leg_closed
            FROM transaction_log tl
                     INNER JOIN shares_log sl ON tl.id = sl.transaction_fk
                     INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk
            WHERE tl.account_fk = ? 
                AND tl.id = ?;
        """

        private const val CREATE_RECORD = "INSERT INTO shares_log (transaction_fk, price, quantity, action_fk) VALUES (?, ?, ?, ?);"

        private const val EDIT_RECORD = "UPDATE shares_log SET price = ?, quantity = ?, action_fk = ? WHERE transaction_fk = ?"

        private const val DELETE_RECORD = "DELETE FROM shares_log WHERE transaction_fk = ?"

    }

    override fun getSummaries(accountId: UUID): Either<DataAccessError, List<TradeSummaryModel>> {
        val parameters = arrayOf(accountId)
        return performSafeCall (
            { jdbcTemplate.query(GET_SUMMARIES, parameters, TradeSummaryModelRowMapper()) },
            { DataAccessError.DatabaseAccessError() }
        )
    }

    override fun createRecord(transactionId: UUID, model: ShareJournalModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(CREATE_RECORD)
                        ps.setObject(1, transactionId, java.sql.Types.OTHER)
                        ps.setDouble(2, model.price)
                        ps.setInt(3, model.quantity)
                        ps.setString(4, model.action.name)
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

    override fun getById(accountId: UUID, transactionId: UUID): Either<DataAccessError, ShareJournalModel> {
        val parameters = arrayOf(accountId, transactionId)

        val dbResponse: Either<DataAccessError, List<ShareJournalModel>> = performSafeCall(
                { jdbcTemplate.query(GET_BY_ID, parameters, ShareJournalModelRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )

        val checkSize: (List<ShareJournalModel>) -> Either<DataAccessError, ShareJournalModel> = {
            when (it.size == 1) {
                true -> Right(it[0])
                false -> Left(DataAccessError.RecordNotFound("Couldn't find share record with id $transactionId"))
            }
        }

        return dbResponse
                .bind(checkSize)

    }

    override fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): Either<DataAccessError, List<ShareJournalModel>> {
        val parameters = arrayOf(accountId, portfolioId, symbol)
        return performSafeCall(
                { jdbcTemplate.query(GET_BY_SYMBOL, parameters, ShareJournalModelRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }

    override fun editRecord(model: ShareJournalModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(EDIT_RECORD)
                        ps.setDouble(1, model.price)
                        ps.setInt(2, model.quantity)
                        ps.setString(3, model.action.name)
                        ps.setObject(4, model.transactionDetails.id, java.sql.Types.OTHER)
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
