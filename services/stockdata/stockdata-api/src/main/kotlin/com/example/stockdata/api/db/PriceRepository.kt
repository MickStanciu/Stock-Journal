package com.example.stockdata.api.db

import com.example.common.converter.TimeConverter
import com.example.stockdata.api.core.model.PriceModel
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class PriceRepository(val jdbcTemplate: JdbcTemplate) {

    fun getBySymbol(symbol: String) : PriceModel? {
        val parameters = arrayOf<String>(symbol)
        val modelList : List<PriceModel> = jdbcTemplate.query(STOCK_DATA_READ_BY_SYMBOL, parameters, PriceModelRowMapper());
        if (modelList.size == 1) {
            return modelList[0]
        }
        return null
    }

    companion object {
        const val STOCK_DATA_READ_BY_SYMBOL: String =
                "SELECT symbol, last_updated_on, last_close " +
                        "FROM price " +
                        "WHERE symbol = ?"
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

