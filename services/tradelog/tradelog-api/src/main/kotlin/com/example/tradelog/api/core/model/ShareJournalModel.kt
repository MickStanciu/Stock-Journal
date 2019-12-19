package com.example.tradelog.api.core.model

class ShareJournalModel(
        val transactionDetails: TransactionModel,
        val price: Double,
        val actualPrice: Double,
        val quantity: Int,
        val action: ActionType
)