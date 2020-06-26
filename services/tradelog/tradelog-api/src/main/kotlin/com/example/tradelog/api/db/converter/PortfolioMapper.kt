package com.example.tradelog.api.db.converter

import com.example.tradelog.api.core.model.PortfolioModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.util.*

class PortfolioMapper: RowMapper<PortfolioModel> {

    override fun mapRow(rs: ResultSet, rowNum: Int): PortfolioModel {
        return PortfolioModel(
                id = rs.getObject("id", UUID::class.java),
                name = rs.getString("name"),
                default = rs.getBoolean("is_default")
        )
    }
}