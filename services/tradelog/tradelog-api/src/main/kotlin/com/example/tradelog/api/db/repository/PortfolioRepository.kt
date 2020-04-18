package com.example.tradelog.api.db.repository

import com.example.tradelog.api.core.model.PortfolioModel
import com.example.tradelog.api.db.converter.PortfolioMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class PortfolioRepository(private val jdbcTemplate: JdbcTemplate) {

    companion object {

        private const val READ_PORTFOLIOS = """
            SELECT id, name, is_default
            FROM portfolio
            WHERE account_fk = CAST(? AS uuid)
            ORDER BY (case when is_default then 1 end), name;
        """
    }

    fun getPortfolios(accountId: String): List<PortfolioModel> {
        val parameters = arrayOf(accountId)
        return jdbcTemplate.query(READ_PORTFOLIOS, parameters, PortfolioMapper())
    }
}