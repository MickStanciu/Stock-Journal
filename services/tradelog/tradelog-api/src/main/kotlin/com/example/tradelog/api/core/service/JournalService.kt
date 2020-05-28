package com.example.tradelog.api.core.service

import com.example.common.repository.DataAccessError
import com.example.common.service.ServiceError
import com.example.common.types.Either
import com.example.tradelog.api.core.model.TradeSummaryModel

interface JournalService<T> {

    fun getSummaries(accountId: String): Either<ServiceError, Map<String, TradeSummaryModel>>
    fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): Either<ServiceError, List<T>>
    fun getById(accountId: String, transactionId: String): Either<ServiceError, T>
    fun createRecord(transactionId: String, model: T): Either<ServiceError, T>
    fun editRecord(model: T): Either<ServiceError, Unit>
    fun deleteRecord(transactionId: String): Either<ServiceError, Unit>

    fun toServiceError(repositoryError: DataAccessError): ServiceError {
        return when (repositoryError) {
            is DataAccessError.RecordNotFound -> ServiceError.RecordNotFound(repositoryError.message)
            is DataAccessError.DatabaseAccessError -> ServiceError.DataAccessError(repositoryError.message)
        }
    }
}
