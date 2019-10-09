package com.example.tradelog.api.spec.model

class ShareJournalModel(
        val transactionDetails: TransactionModel,
        val price: Double,
        val actualPrice: Double,
        val quantity: Int,
        val action: Action
)