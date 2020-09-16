package com.example.tradelog.api.core.service

import arrow.core.Either
import com.example.common.service.ServiceError
import com.example.tradelog.api.core.model.TradeSummaryModel
import java.util.*

interface JournalService<T> {

    fun getSummaries(accountId: UUID): Either<ServiceError, Map<String, TradeSummaryModel>>
    fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): Either<ServiceError, List<T>>
    fun getById(accountId: UUID, transactionId: UUID): Either<ServiceError, T>
    fun createRecord(transactionId: UUID, model: T): Either<ServiceError, T>
    fun editRecord(model: T): Either<ServiceError, Unit>
    fun deleteRecord(transactionId: UUID): Either<ServiceError, Unit>

//    fun toServiceError(repositoryError: DataAccessError): ServiceError {
//        return when (repositoryError) {
//            is DataAccessError.RecordNotFound -> ServiceError.RecordNotFound(repositoryError.message)
//            is DataAccessError.DatabaseAccessError -> ServiceError.DataAccessError(repositoryError.message)
//        }
//    }
}
