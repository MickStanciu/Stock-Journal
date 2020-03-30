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

        //TODO: check password here
        val encrypted = getEncryptedPassword(password, model.rainbow)
        if (encrypted == model.password) {
            return model
        }
        LOG.error("Authentication failure for {}", model.loginName)
        return null
    }

    fun getEncryptedPassword(textPassword: String, rainbow: String): String {
        val encrypted = "";

        return encrypted;
    }
}