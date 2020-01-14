package com.example.gateway.api.core.model

class TransactionSettingsModel(
        val transactionId: String,
        val preferredPrice: Double,
        val groupSelected: Boolean,
        val legClosed: Boolean)