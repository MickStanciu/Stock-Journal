package com.example.tradelog.api.db.repository

import arrow.core.Either
import com.example.common.repository.DataAccessError
import com.example.tradelog.api.core.model.TradeSummaryModel
import java.util.*

interface JournalRepository<T> {

    fun getSummaries(accountId: UUID): Either<DataAccessError, List<TradeSummaryModel>>
    fun createRecord(transactionId: UUID, model: T): Either<DataAccessError, Unit>
    fun getById(accountId: UUID, transactionId: UUID): Either<DataAccessError, T>
    fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): Either<DataAccessError, List<T>>
    fun editRecord(model: T): Either<DataAccessError, Unit>
    fun deleteRecord(transactionId: UUID): Either<DataAccessError, Unit>
}