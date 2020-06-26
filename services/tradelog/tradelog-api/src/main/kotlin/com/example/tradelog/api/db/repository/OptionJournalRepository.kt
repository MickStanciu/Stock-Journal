package com.example.tradelog.api.db.repository

import com.example.common.converter.TimeConverter
import com.example.common.repository.DataAccessError
import com.example.common.types.Either
import com.example.common.types.Either.Companion.bind
import com.example.common.types.Either.Left
import com.example.common.types.Either.Right
import com.example.common.utils.performSafeCall
import com.example.tradelog.api.core.model.OptionJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.converter.OptionJournalModelRowMapper
import com.example.tradelog.api.db.converter.TradeSummaryModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.Connection
import java.util.*

@Service
class OptionJournalRepository(private val jdbcTemplate: JdbcTemplate) : JournalRepository<OptionJournalModel> {

    companion object {
        private const val GET_SUMMARIES = """
            SELECT tl.symbol,
                   ol.action_fk,
                   ol.premium,
                   tl.broker_fees,
                   ol.contract_number,
                   tl.transaction_type_fk,
                   tsl.leg_closed
            FROM transaction_log tl
                     INNER JOIN option_log ol ON tl.id = ol.transaction_fk
                     INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk
            WHERE tl.account_fk = ?
              AND transaction_type_fk = 'OPTION'
              AND (tsl.leg_closed = true OR now() > ol.expiry_date)
            ORDER BY tl.symbol;
        """

        private const val GET_BY_ID = """
            SELECT tl.id,
                    tl.account_fk,
                    tl.portfolio_fk,
                    tl.date,
                    tl.symbol,
                    tl.transaction_type_fk,
                    ol.stock_price,
                    ol.strike_price,
                    ol.expiry_date,
                    ol.contract_number,
                    ol.premium,
                    ol.action_fk,
                    ol.option_type_fk,
                    tl.broker_fees,
                    tsl.preferred_price,
                    tsl.group_selected,
                    tsl.leg_closed
                    FROM transaction_log tl
                      INNER JOIN option_log ol on tl.id = ol.transaction_fk
                      INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk
                    WHERE tl.account_fk = ? AND tl.id = ?;
        """

        private const val GET_BY_SYMBOL = """
                SELECT tl.id,
                    tl.account_fk,
                    tl.portfolio_fk,
                    tl.date,
                    tl.symbol,
                    tl.transaction_type_fk,
                    ol.stock_price,
                    ol.strike_price,
                    ol.expiry_date,
                    ol.contract_number,
                    ol.premium,
                    ol.action_fk,
                    ol.option_type_fk,
                    tl.broker_fees,
                    tsl.preferred_price,
                    tsl.group_selected,
                    tsl.leg_closed
                    FROM transaction_log tl
                      INNER JOIN option_log ol on tl.id = ol.transaction_fk
                      INNER JOIN transaction_settings_log tsl on tl.id = tsl.transaction_fk
                    WHERE account_fk = ? 
                        AND tl.portfolio_fk = ?
                        AND tl.transaction_type_fk = 'OPTION' 
                        AND symbol = ?
                    ORDER BY date;
        """

        private const val CREATE_RECORD = """
            INSERT INTO option_log (transaction_fk, stock_price, strike_price, expiry_date, contract_number, premium, action_fk, option_type_fk)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """

        private const val EDIT_RECORD = """
            UPDATE option_log SET stock_price = ?, strike_price = ?, expiry_date = ?, contract_number = ?, premium = ?, action_fk = ?, option_type_fk = ? 
            WHERE transaction_fk = ?;
        """

        private const val DELETE_RECORD = "DELETE FROM option_log WHERE transaction_fk = ? and option_type_fk in ('CALL', 'PUT')";
    }

    override fun getSummaries(accountId: UUID): Either<DataAccessError, List<TradeSummaryModel>> {
        val parameters = arrayOf(accountId)
        return performSafeCall(
                { jdbcTemplate.query(GET_SUMMARIES, parameters, TradeSummaryModelRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }

    override fun createRecord(transactionId: UUID, model: OptionJournalModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(CREATE_RECORD)
                        ps.setObject(1, transactionId, java.sql.Types.OTHER)
                        ps.setDouble(2, model.stockPrice)
                        ps.setDouble(3, model.strikePrice)
                        ps.setTimestamp(4, TimeConverter.fromOffsetDateTime(model.expiryDate))
                        ps.setInt(5, model.contracts)
                        ps.setDouble(6, model.premium)
                        ps.setString(7, model.action.name)
                        ps.setString(8, model.optionType.name)
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

    override fun getById(accountId: UUID, transactionId: UUID): Either<DataAccessError, OptionJournalModel> {
        val parameters = arrayOf(accountId, transactionId)

        val dbResponse: Either<DataAccessError, List<OptionJournalModel>> = performSafeCall(
                { jdbcTemplate.query(GET_BY_ID, parameters, OptionJournalModelRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )

        val checkSize: (List<OptionJournalModel>) -> Either<DataAccessError, OptionJournalModel> = {
            when (it.size == 1) {
                true -> Right(it[0])
                false -> Left(DataAccessError.RecordNotFound("Couldn't find option record with id $transactionId"))
            }
        }

        return dbResponse
                .bind(checkSize)
    }

    override fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): Either<DataAccessError, List<OptionJournalModel>> {
        val parameters = arrayOf(accountId, portfolioId, symbol)
        return performSafeCall(
                { jdbcTemplate.query(GET_BY_SYMBOL, parameters, OptionJournalModelRowMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }

    override fun editRecord(model: OptionJournalModel): Either<DataAccessError, Unit> {
        val dbResponse: Either<DataAccessError, Boolean> = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(EDIT_RECORD)
                        ps.setDouble(1, model.stockPrice)
                        ps.setDouble(2, model.strikePrice)
                        ps.setTimestamp(3, TimeConverter.fromOffsetDateTime(model.expiryDate))
                        ps.setInt(4, model.contracts)
                        ps.setDouble(5, model.premium)
                        ps.setString(6, model.action.name)
                        ps.setString(7, model.optionType.name)
                        ps.setObject(8, model.transactionDetails.id, java.sql.Types.OTHER)
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