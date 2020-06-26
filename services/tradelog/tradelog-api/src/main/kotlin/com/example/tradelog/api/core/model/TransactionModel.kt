package com.example.tradelog.api.core.model

import java.time.OffsetDateTime
import java.util.*

data class TransactionModel (
        val id: UUID,
        val accountId: UUID,
        val portfolioId: UUID,
        val date: OffsetDateTime,
        val symbol: String,
        val type: TransactionType,
        val brokerFees: Double,
        val settings: TransactionSettingsModel)
