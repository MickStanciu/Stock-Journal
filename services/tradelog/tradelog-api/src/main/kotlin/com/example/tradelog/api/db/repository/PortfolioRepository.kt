package com.example.tradelog.api.db.repository

import com.example.common.repository.DataAccessError
import com.example.common.types.Either
import com.example.common.types.Either.Companion.bind
import com.example.common.types.Either.Companion.performSafeCall
import com.example.common.types.Either.Error
import com.example.common.types.Either.Value
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

        private const val READ_DEFAULT_PORTFOLIO = """
            SELECT id, name, is_default
            FROM portfolio
            WHERE account_fk = CAST(? AS uuid)
            AND is_default = true
        """
    }

    fun getPortfolios(accountId: String): Either<DataAccessError, List<PortfolioModel>> {
        val parameters = arrayOf(accountId)

        return performSafeCall(
                { jdbcTemplate.query(READ_PORTFOLIOS, parameters, PortfolioMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )
    }

    fun getDefaultPortfolio(accountId: String): Either<DataAccessError, PortfolioModel> {
        val parameters = arrayOf(accountId)

        val dbResponse: Either<DataAccessError, List<PortfolioModel>> = performSafeCall(
                { jdbcTemplate.query(READ_DEFAULT_PORTFOLIO, parameters, PortfolioMapper()) },
                { DataAccessError.DatabaseAccessError() }
        )

        val checkResponse: (List<PortfolioModel>) -> Either<DataAccessError, PortfolioModel> = { models ->
            if (models.size == 1)
                Value(models[0])
            else
                Error(DataAccessError.DatabaseAccessError())

        }

        return dbResponse
                .bind(checkResponse)
    }
}