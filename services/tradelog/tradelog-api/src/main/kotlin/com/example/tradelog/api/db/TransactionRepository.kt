package com.example.tradelog.api.db

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
class TransactionRepository(private val jdbcTemplate: JdbcTemplate) {

    fun getUniqueSymbols(accountId: String): List<String> {
        val parameters = arrayOf(accountId)
        return jdbcTemplate.query(JOURNAL_READ_SYMBOLS, parameters, SymbolRowMapper())
    }

    companion object {
        private const val JOURNAL_READ_SYMBOLS = "SELECT DISTINCT symbol " +
                "FROM transaction_log " +
                "WHERE account_fk = CAST(? AS uuid)" +
                "ORDER BY symbol ASC"
    }
}

private class SymbolRowMapper : RowMapper<String> {
    override fun mapRow(rs: ResultSet, rowNum: Int): String {
        return rs.getString("symbol")
    }
}