package com.example.gateway.api.core.model

data class AccountModel(
        var id: String,
        var loginName: String,
        var active: Boolean,
        var email: String,
        var displayName: String)