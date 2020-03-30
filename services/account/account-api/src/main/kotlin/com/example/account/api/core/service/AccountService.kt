package com.example.account.api.core.service

import com.example.account.api.core.model.AccountModel
import com.example.account.api.db.repository.AccountRepository
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Service

@Service
class AccountService(private val repository: AccountRepository) {
    companion object {
        private val LOG = LoggerFactory.getLogger(AccountService::class.java)
    }

    fun getAccount(username: String, password: String): AccountModel? {
        val model = repository.getAccount(username) ?: return null

        val valid = HashUtils.validatePassword(password, model.password)
        if (valid) {
            return model
        }
        LOG.error("Authentication failure for {}", model.loginName)
        return null
    }

}