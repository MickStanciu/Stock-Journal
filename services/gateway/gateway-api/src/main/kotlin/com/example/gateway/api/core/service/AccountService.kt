package com.example.gateway.api.core.service

import com.example.gateway.api.core.model.AccountModel
import com.example.gateway.api.rest.gateway.AccountGateway
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountGateway: AccountGateway) {

    fun getActiveAccount(username: String, password: String): AccountModel? {
        val account = accountGateway.getAccount(username, password) ?: return null

        return if (account.active) {
            account
        } else {
            null
        }
    }
}