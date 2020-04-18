package com.example.tradelog.api.core.model

import java.time.OffsetDateTime

class TransactionModel (
        val id: String,
        val accountId: String,
        val portfolioId: String,
        val date: OffsetDateTime,
        val symbol: String,
        val type: TransactionType,
        val brokerFees: Double,
        val settings: TransactionSettingsModel)
