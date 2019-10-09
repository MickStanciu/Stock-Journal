package com.example.tradelog.api.spec.model

import java.time.OffsetDateTime

class TransactionModel (
        val id: String,
        val accountId: String,
        val date: OffsetDateTime,
        val symbol: String,
        val type: TransactionType,
        val brokerFees: Double,
        val settings: TransactionSettingsModel)
