package com.example.tradelog.api.db.converter

import com.example.tradelog.api.core.model.SummaryMatrixModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class SummaryMatrixRowMapper : RowMapper<SummaryMatrixModel> {
    override fun mapRow(rs: ResultSet, rowNum: Int): SummaryMatrixModel {
        return SummaryMatrixModel(
                year = rs.getInt("year"),
                month = rs.getInt("month"),
                total = rs.getDouble("total")
        )
    }
}