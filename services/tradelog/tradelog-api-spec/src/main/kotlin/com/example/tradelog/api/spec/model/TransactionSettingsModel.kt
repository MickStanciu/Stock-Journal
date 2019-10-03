package com.example.tradelog.api.spec.model

class TransactionSettingsModel(
        val transactionId: String,
        val preferredPrice: Double,
        val groupSelected: Boolean,
        val legClosed: Boolean)
