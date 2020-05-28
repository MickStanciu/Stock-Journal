package com.example.tradelog.api.db.repository

import com.example.common.converter.TimeConverter
import com.example.common.repository.DataAccessError
import com.example.common.types.Either
import com.example.common.types.Either.Companion.bind
import com.example.common.types.Either.Left
import com.example.common.types.Either.Right
import com.example.common.utils.performSafeCall
import com.example.tradelog.api.core.model.SummaryMatrixModel
import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.db.converter.SummaryMatrixRowMapper
import com.example.tradelog.api.db.converter.SymbolRowMapper
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Service
import java.sql.Connection
import java.sql.Statement

@Service
class TransactionRepository(private val jdbcTemplate: JdbcTemplate) {

    companion object {
        private val LOG = LoggerFactory.getLogger(TransactionRepository::class.java)

        //TODO: not sure this one works or it's used
        private const val READ_SYMBOLS = """
            SELECT DISTINCT tl.symbol
            FROM transaction_log tl
            INNER JOIN portfolio p ON tl.portfolio_fk = p.id
            WHERE tl.account_fk = CAST(? AS uuid)
            AND tl.portfolio_fk = CAST(? AS uuid)
            ORDER BY tl.symbol;
        """

        private const val READ_LAST_YEAR_SYMBOLS = """
            SELECT DISTINCT symbol
            FROM transaction_log
            WHERE date > date_trunc('month', CURRENT_DATE) - INTERVAL '1 year'
            ORDER BY symbol;
        """

        private const val CREATE_RECORD = """
            INSERT INTO transaction_log (account_fk, portfolio_fk, date, symbol, transaction_type_fk, broker_fees)
                    VALUES (CAST(? AS uuid), CAST(? AS uuid), ?, ?, ?, ?);
        """

        private const val SUMMARY_MATRIX = """
            SELECT EXTRACT('year' FROM tl.date) as year, EXTRACT('month' FROM tl.date) as month,
                       COALESCE(ol.premium * ol.contract_number * 100, 0) * CASE WHEN ol.action_fk = 'BUY' THEN -1 ELSE 1 END +
                       COALESCE(dl.dividend * dl.quantity, 0) -
                       tl.broker_fees as total
            FROM transaction_log tl
            INNER JOIN transaction_settings_log tsl ON tl.id = tsl.transaction_fk
            INNER JOIN portfolio p ON tl.portfolio_fk = p.id
            LEFT JOIN option_log ol on tl.id = ol.transaction_fk
            LEFT JOIN dividend_log dl on tl.id = dl.transaction_fk
            WHERE tsl.leg_closed = true
                AND tl.date > date_trunc('month', CURRENT_DATE) - INTERVAL '5 year'
                AND tl.symbol != 'XYZ'
                AND tl.transaction_type_fk in ('OPTION', 'DIVIDEND')
                AND tl.account_fk = CAST(? AS uuid)
                AND tl.portfolio_fk = CAST(? AS uuid)
            ORDER BY year, month;
        """

        private const val UPDATE_RECORD = "UPDATE transaction_log SET broker_fees = ?, date = ? where id = CAST(? AS uuid) and account_fk = CAST(? AS uuid);"

        private const val DELETE_RECORD = "DELETE FROM transaction_log WHERE id = CAST(? AS uuid) and account_fk = CAST(? AS uuid);"

        private const val CREATE_SETTINGS = "INSERT INTO transaction_settings_log (transaction_fk, preferred_price, group_selected, leg_closed) VALUES (CAST(? AS uuid), ?, ?, ?);"

        private const val EDIT_SETTINGS = "UPDATE transaction_settings_log SET preferred_price = ?, group_selected = ?, leg_closed = ? WHERE transaction_fk = CAST(? AS uuid);"

        private const val DELETE_SETTINGS = "DELETE FROM transaction_settings_log WHERE transaction_fk = CAST(? AS uuid);"
    }

    fun getUniqueSymbols(accountId: String, portfolioId: String): Either<DataAccessError, List<String>> {
        val parameters = arrayOf(accountId, portfolioId)
        return performSafeCall(
                { jdbcTemplate.query(READ_SYMBOLS, parameters, SymbolRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }

    fun getActiveSymbols(): Either<DataAccessError, List<String>> {
        return performSafeCall(
                { jdbcTemplate.query(READ_LAST_YEAR_SYMBOLS, SymbolRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }

    fun createSettings(transactionId: String, model: TransactionSettingsModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(CREATE_SETTINGS)
                        ps.setString(1, transactionId)
                        ps.setDouble(2, model.preferredPrice)
                        ps.setBoolean(3, model.groupSelected)
                        ps.setBoolean(4, model.legClosed)
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

    fun updateSettings(model: TransactionSettingsModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(EDIT_SETTINGS)
                        ps.setDouble(1, model.preferredPrice)
                        ps.setBoolean(2, model.groupSelected)
                        ps.setBoolean(3, model.legClosed)
                        ps.setString(4, model.transactionId)
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

    fun deleteSettings(transactionId: String): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(DELETE_SETTINGS)
                        ps.setString(1, transactionId)
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

    fun updateRecord(model: TransactionModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(UPDATE_RECORD)
                        ps.setDouble(1, model.brokerFees)
                        ps.setTimestamp(2, TimeConverter.fromOffsetDateTime(model.date))
                        ps.setString(3, model.id)
                        ps.setString(4, model.accountId)
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

    fun createRecord(model: TransactionModel): Either<DataAccessError, String> {
        val dbResponse: Either<DataAccessError, GeneratedKeyHolder> = performSafeCall(
                {
                    val keyHolder = GeneratedKeyHolder()
                    jdbcTemplate.update({ connection: Connection ->
                        val ps = connection.prepareStatement(CREATE_RECORD, Statement.RETURN_GENERATED_KEYS)
                        ps.setString(1, model.accountId)
                        ps.setString(2, model.portfolioId)
                        ps.setTimestamp(3, TimeConverter.fromOffsetDateTime(model.date))
                        ps.setString(4, model.symbol)
                        ps.setString(5, model.type.name)
                        ps.setDouble(6, model.brokerFees)
                        ps
                    }, keyHolder)
                    keyHolder
                },
                { DataAccessError.DatabaseAccessError() }
        )

        val checkResponse: (GeneratedKeyHolder) -> Either<DataAccessError, String> = { keyHolder ->
            if (keyHolder.keyList.isEmpty()) {
                Left(DataAccessError.DatabaseAccessError("Failed to insert transaction"))
            }

            val key = keyHolder.keyList[0]["id"].toString()
            if (key.trim().isEmpty()) {
                Left(DataAccessError.DatabaseAccessError("Failed to insert transaction"))
            }
            Right(key)
        }

        return dbResponse
                .bind(checkResponse)
    }

    fun deleteRecord(accountId: String, transactionId: String): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(DELETE_RECORD)
                        ps.setString(1, transactionId)
                        ps.setString(2, accountId)
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

    fun getSummaryMatrix(accountId: String, portfolioId: String): Either<DataAccessError, List<SummaryMatrixModel>> {
        val parameters = arrayOf(accountId, portfolioId)
        return performSafeCall(
                { jdbcTemplate.query(SUMMARY_MATRIX, parameters, SummaryMatrixRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }
}

