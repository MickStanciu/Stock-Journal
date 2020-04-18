package com.example.tradelog.api.db.converter

import com.example.tradelog.api.core.model.PortfolioModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class PortfolioMapper: RowMapper<PortfolioModel> {

    override fun mapRow(rs: ResultSet, rowNum: Int): PortfolioModel {
        return PortfolioModel(
                id = rs.getString("id"),
                name = rs.getString("name"),
                default = rs.getBoolean("is_default")
        )
    }
}