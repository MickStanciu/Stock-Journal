package com.example.tradelog.api.spec.model

import java.time.OffsetDateTime

class OptionJournalModel(
        val transactionDetails: TransactionModel,
        val stockPrice: Double,
        val strikePrice: Double,
        val expiryDate: OffsetDateTime,
        val contracts: Int,
        val premium: Double,
        val action: Action,
        val optionType: OptionType,
        val brokerFees: Double
)
