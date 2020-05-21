package com.example.tradelog.api.db.repository

import com.example.common.repository.DataAccessError
import com.example.common.types.Either
import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalRepository<T> {

    fun getSummaries(accountId: String): Either<DataAccessError, List<TradeSummaryModel>>
    fun createRecord(transactionId: String, model: T): Either<DataAccessError, Unit>
    fun getById(accountId: String, transactionId: String): Either<DataAccessError, T>
    fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): Either<DataAccessError, List<T>>
    fun editRecord(model: T): Either<DataAccessError, Unit>
    fun deleteRecord(transactionId: String): Either<DataAccessError, Unit>
}