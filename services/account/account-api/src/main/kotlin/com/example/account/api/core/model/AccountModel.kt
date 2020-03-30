package com.example.account.api.core.model

data class AccountModel(val id: String,
                        val active: Boolean,
                        val email: String,
                        val password: String,
                        val displayName: String,
                        val loginName: String)