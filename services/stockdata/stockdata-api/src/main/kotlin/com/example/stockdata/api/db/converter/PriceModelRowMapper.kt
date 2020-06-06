package com.example.stockdata.api.db.converter

import com.example.common.converter.TimeConverter
import com.example.stockdata.api.core.model.PriceModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class PriceModelRowMapper : RowMapper<PriceModel> {
    override fun mapRow(rs: ResultSet, rowNum: Int): PriceModel {
        return PriceModel(
                symbol = rs.getString("symbol"),
                lastClose = rs.getDouble("last_close"),
                lastUpdatedOn = TimeConverter.fromTimestamp(rs.getTimestamp("last_updated_on")),
                lastFailedOn = rs.getTimestamp("last_failed_on")?.let { TimeConverter.fromTimestamp(it) },
                active = rs.getBoolean("active")
        )
    }
}
