package com.example.tradelog.api.spec.model

class DividendJournalModel(
        val transactionDetails: TransactionModel,
        val dividend: Double,
        val quantity: Int)

