package com.example.tradelog.api.db.repository

import com.example.common.error.DataAccessError
import com.example.common.types.Either
import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalRepository<T> {

    fun getSummaries(accountId: String): List<TradeSummaryModel>
    fun createRecord(transactionId: String, model: T)
    fun getById(accountId: String, transactionId: String): Either<DataAccessError, T>
    fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): List<T>
    fun editRecord(model: T): Boolean
    fun deleteRecord(transactionId: String): Boolean
}