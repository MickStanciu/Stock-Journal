package com.example.account.api.db.repository

import com.example.account.api.core.model.AccountModel
import com.example.account.api.db.converter.AccountModelRowMapper
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class AccountRepository(private val jdbcTemplate: JdbcTemplate) {

    companion object {
        private val LOG = LoggerFactory.getLogger(AccountRepository::class.java)

        private const val GET_ACCOUNT = """
            SELECT acs.id, ad.display_name, ad.login_name, ad.email, ad.active
            FROM account_secure acs
            INNER JOIN account_detail ad ON acs.id = ad.account_fk
            WHERE login_name = ? AND password = ?;
        """
    }

    fun getAccount(loginName: String, password: String): AccountModel? {
        val parameters = arrayOf<Any>(loginName, password)
        val models = jdbcTemplate.query(GET_ACCOUNT, parameters, AccountModelRowMapper())
        if (models.size == 1) {
            return models[0]
        }
        return null
    }
}