package com.example.stockdata.api.db.repository

import arrow.core.Either
import arrow.core.flatMap
import com.example.common.converter.TimeConverter
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.common.utils.performSafeCall
import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.db.converter.PriceModelRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Connection

@Repository
class PriceRepository(private val jdbcTemplate: JdbcTemplate) {

    fun getBySymbol(symbol: String): Either<ApiException, PriceModel> {
        val parameters = arrayOf(symbol)

        val dbResponse = performSafeCall(
                { jdbcTemplate.query(STOCK_DATA_READ_BY_SYMBOL, parameters, PriceModelRowMapper()) },
                { ApiException(ApiExceptionCode.DATABASE_ACCESS_ERROR, it) }
        )

        val checkResponse: (List<PriceModel>) -> Either<ApiException, PriceModel> = {
            when (it.size) {
                0 -> Either.Left(ApiException(ApiExceptionCode.DATABASE_RECORD_NOT_FOUND, "Record not found for $symbol"))
                1 -> Either.Right(it[0])
                else -> Either.Left(ApiException(ApiExceptionCode.DATABASE_MORE_THAN_ONE_RECORD))
            }
        }

        return dbResponse
                .flatMap(checkResponse)
    }

    fun updateForSymbol(priceModel: PriceModel): Either<ApiException, Unit> {

        val dbResponse = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(STOCK_PRICE_UPSERT)
                        ps.setString(1, priceModel.symbol)
                        ps.setDouble(2, priceModel.lastClose)
                        ps.setTimestamp(3, TimeConverter.fromOffsetDateTime(priceModel.lastUpdatedOn))
                        ps.setTimestamp(4, if (priceModel.lastFailedOn != null) TimeConverter.fromOffsetDateTime(priceModel.lastFailedOn!!) else null)
                        ps.setBoolean(5, priceModel.active)
                        ps
                    }
                },
                { ApiException(ApiExceptionCode.DATABASE_ACCESS_ERROR, it) }
        )

        val checkResponse: (Int) -> Either<ApiException, Unit> = {
            when (it) {
                1 -> Either.Right(Unit)
                else -> Either.Left(ApiException(ApiExceptionCode.DATABASE_ACCESS_ERROR, "Could not update for ${priceModel.symbol}"))
            }
        }

        return dbResponse
                .flatMap(checkResponse)
    }

    fun getNotUpdatedSymbols(adjustedLimit: Int): Either<ApiException, List<PriceModel>> {
        val parameters = arrayOf(adjustedLimit)

        return performSafeCall(
                { jdbcTemplate.query(GET_OLDEST_PRICES, parameters, PriceModelRowMapper()) },
                { ApiException(ApiExceptionCode.DATABASE_ACCESS_ERROR, it) }
        )
    }

    fun addSymbol(priceModel: PriceModel): Either<ApiException, Unit> {
        val dbResponse = performSafeCall(
                {
                    jdbcTemplate.update { connection: Connection ->
                        val ps = connection.prepareStatement(STOCK_PRICE_ADD)
                        ps.setString(1, priceModel.symbol)
                        ps.setDouble(2, priceModel.lastClose)
                        ps.setTimestamp(3, TimeConverter.fromOffsetDateTime(priceModel.lastUpdatedOn))
                        ps
                    }
                },
                { ApiException(ApiExceptionCode.DATABASE_ACCESS_ERROR, it) }
        )

        val checkResponse: (Int) -> Either<ApiException, Unit> = {
            when (it) {
                1 -> Either.Right(Unit)
                else -> Either.Left(ApiException(ApiExceptionCode.DATABASE_ACCESS_ERROR, "Could not add for ${priceModel.symbol}"))
            }
        }

        return dbResponse
                .flatMap(checkResponse)
    }

    companion object {
        private const val STOCK_DATA_READ_BY_SYMBOL: String = """
                SELECT symbol, last_updated_on, last_close, last_failed_on, active
                FROM price
                WHERE symbol = ?
                """

        private const val STOCK_PRICE_UPSERT: String = """
            INSERT INTO price(symbol, last_close, last_updated_on, last_failed_on, active)
            VALUES (?, ?, ?, ?, ?)
            ON CONFLICT ON CONSTRAINT price_pkey
                DO UPDATE SET last_close      = excluded.last_close,
                              last_updated_on = excluded.last_updated_on,
                              last_failed_on  = excluded.last_failed_on,
                              active          = excluded.active
        """


        private const val STOCK_PRICE_ADD: String = """
                INSERT INTO price(symbol, last_close, last_updated_on, last_failed_on, active)
                VALUES(?, ?, ?, null, true)
                ON CONFLICT ON CONSTRAINT price_pkey
                DO NOTHING;
        """

        private const val GET_OLDEST_PRICES: String = """
            SELECT symbol, last_close, last_updated_on, last_failed_on, active FROM price
            WHERE last_updated_on < CURRENT_DATE
              AND active = TRUE
            ORDER BY  last_failed_on DESC, last_updated_on
            LIMIT ?
        """
    }
}

