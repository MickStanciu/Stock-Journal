package com.example.tradelog.api.core.model

data class DividendJournalModel(
        val transactionDetails: TransactionModel,
        val dividend: Double,
        val quantity: Int)

