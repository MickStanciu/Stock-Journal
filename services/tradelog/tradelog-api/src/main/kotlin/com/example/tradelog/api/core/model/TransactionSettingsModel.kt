package com.example.tradelog.api.core.model

import java.util.*

data class TransactionSettingsModel(
        val transactionId: UUID,
        val preferredPrice: Double,
        val groupSelected: Boolean,
        val legClosed: Boolean)
