package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalService<T> {

    fun getSummaries(accountId: String): Map<String, TradeSummaryModel>
    fun getAllBySymbol(accountId: String, symbol: String): List<T>
    fun getById(accountId: String, transactionId: String): T?
    fun createRecord(transactionId: String, model: T): T?
    fun editRecord(model: T): Boolean
    fun deleteRecord(transactionId: String): Boolean
}
