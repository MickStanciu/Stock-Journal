package com.example.gateway.api.core.model

import java.util.*

data class AccountModel(
        var id: UUID,
        var loginName: String,
        var active: Boolean,
        var email: String,
        var displayName: String)