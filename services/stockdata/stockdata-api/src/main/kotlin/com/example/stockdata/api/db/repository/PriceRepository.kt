package com.example.stockdata.api.db.repository

import com.example.common.converter.TimeConverter
import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.db.converter.PriceModelRowMapper
import com.example.stockdata.api.db.converter.SymbolRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Connection

@Repository
class PriceRepository(private val jdbcTemplate: JdbcTemplate) {

    fun getBySymbol(symbol: String) : PriceModel? {
        val parameters = arrayOf(symbol)
        val modelList : List<PriceModel> = jdbcTemplate.query(STOCK_DATA_READ_BY_SYMBOL, parameters, PriceModelRowMapper());
        if (modelList.size == 1) {
            return modelList[0]
        }
        return null
    }

    fun updateForSymbol(priceModel: PriceModel) {
        jdbcTemplate.update { connection: Connection ->
            val ps = connection.prepareStatement(STOCK_PRICE_UPSERT)
            ps.setString(1, priceModel.symbol)
            ps.setDouble(2, priceModel.lastClose)
            ps.setTimestamp(3, TimeConverter.fromOffsetDateTime(priceModel.lastUpdatedOn))
            ps
        }

    }

    fun getNotUpdatedSymbols(adjustedLimit: Int): List<String> {
        val parameters = arrayOf(adjustedLimit)
        return jdbcTemplate.query(GET_OLDEST_PRICES, parameters, SymbolRowMapper())
    }

    fun updateSymbols(symbolsList: List<String>) {
        println(symbolsList)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val STOCK_DATA_READ_BY_SYMBOL: String =
                "SELECT symbol, last_updated_on, last_close " +
                        "FROM price " +
                        "WHERE symbol = ?"

        private const val STOCK_PRICE_UPSERT: String =
                "INSERT INTO price(symbol, last_close, last_updated_on) " +
                        "VALUES(?, ?, ?) " +
                        "ON CONFLICT ON CONSTRAINT price_pkey " +
                        "DO UPDATE SET last_close = excluded.last_close, last_updated_on = excluded.last_updated_on"

        private const val GET_OLDEST_PRICES: String = "SELECT symbol FROM price ORDER BY last_updated_on ASC LIMIT ?"
    }
}

