package com.example.account.api.core.model

import java.util.*

data class AccountModel(val id: UUID,
                        val active: Boolean,
                        val email: String,
                        val password: String,
                        val displayName: String,
                        val loginName: String)