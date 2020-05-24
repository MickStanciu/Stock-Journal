package com.example.tradelog.api.core.service

import com.example.common.service.ServiceError
import com.example.common.types.Either
import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalService<T> {

    fun getSummaries(accountId: String): Either<ServiceError, Map<String, TradeSummaryModel>>
    fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): Either<ServiceError, List<T>>
    fun getById(accountId: String, transactionId: String): Either<ServiceError, T>
    fun createRecord(transactionId: String, model: T): Either<ServiceError, T>
    fun editRecord(model: T): Either<ServiceError, T>
    fun deleteRecord(transactionId: String): Either<ServiceError, Unit>
}
