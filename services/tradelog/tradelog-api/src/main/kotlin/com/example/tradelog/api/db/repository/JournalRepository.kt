package com.example.tradelog.api.db.repository

import com.example.common.error.DataAccessError
import com.example.common.types.Either
import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalRepository<T> {

    fun getSummaries(accountId: String): Either<DataAccessError, List<TradeSummaryModel>>
    fun createRecord(transactionId: String, model: T)
    fun getById(accountId: String, transactionId: String): Either<DataAccessError, T>
    fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): Either<DataAccessError, List<T>>
    fun editRecord(model: T): Boolean
    fun deleteRecord(transactionId: String): Boolean

    fun <V> performDataBaseCall(f: () -> V): Either<DataAccessError.DatabaseAccessError, V> =
            try {
                Either.Value(f())
            } catch (exception: Exception) {
                Either.Error(DataAccessError.DatabaseAccessError(exception.toString()))
            }
}