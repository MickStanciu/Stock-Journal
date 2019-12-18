package com.example.stockdata.api.db

import com.example.common.converter.TimeConverter
import com.example.stockdata.api.core.model.PriceModel
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.ResultSet

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

    companion object {
        const val STOCK_DATA_READ_BY_SYMBOL: String =
                "SELECT symbol, last_updated_on, last_close " +
                        "FROM price " +
                        "WHERE symbol = ?"

        const val STOCK_PRICE_UPSERT: String =
                "INSERT INTO price(symbol, last_close, last_updated_on) " +
                        "VALUES(?, ?, ?) " +
                        "ON CONFLICT ON CONSTRAINT price_pkey " +
                        "DO UPDATE SET last_close = excluded.last_close, last_updated_on = excluded.last_updated_on"
    }

}


private class PriceModelRowMapper : RowMapper<PriceModel> {
    override fun mapRow(rs: ResultSet, rowNum: Int): PriceModel {
        return PriceModel(
                symbol = rs.getString("symbol"),
                lastClose = rs.getDouble("last_close"),
                lastUpdatedOn = TimeConverter.fromTimestamp(rs.getTimestamp("last_updated_on"))
        )
    }
}

