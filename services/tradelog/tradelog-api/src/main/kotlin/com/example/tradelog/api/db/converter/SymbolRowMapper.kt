package com.example.tradelog.api.db.converter

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class SymbolRowMapper: RowMapper<String> {
    
    override fun mapRow(rs: ResultSet, rowNum: Int): String {
        return rs.getString("symbol")
    }
}
