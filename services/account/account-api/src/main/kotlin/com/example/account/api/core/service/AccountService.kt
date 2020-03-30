package com.example.account.api.core.service

import com.example.account.api.core.model.AccountModel
import com.example.account.api.db.repository.AccountRepository

import org.springframework.stereotype.Service

@Service
class AccountService(private val repository: AccountRepository) {
    fun getAccount(username: String, password: String): AccountModel? {
        return repository.getAccount(username, password)
    }
}