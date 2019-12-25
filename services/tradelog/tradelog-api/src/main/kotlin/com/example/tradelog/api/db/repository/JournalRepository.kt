package com.example.tradelog.api.db.repository

import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalRepository<T> {

    fun getSummaries(accountId: String): List<TradeSummaryModel>
    fun createRecord(transactionId: String, model: T)
    fun getById(accountId: String, transactionId: String): T?
    fun getAllBySymbol(accountId: String, symbol: String): List<T>
    fun editRecord(model: T): Boolean
    fun deleteRecord(transactionId: String): Boolean
}