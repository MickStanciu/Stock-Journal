package com.example.tradelog.api.core.service

import arrow.core.Either
import arrow.core.flatMap
import com.example.common.service.ServiceError
import com.example.common.service.toServiceError
import com.example.tradelog.api.core.model.SummaryMatrixModel
import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.db.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService(private val repository: TransactionRepository) {

    fun getActiveSymbols(): Either<ServiceError, List<String>> {
        return repository.getActiveSymbols()
                .mapLeft(::toServiceError)
    }

    fun updateSettings(model: TransactionSettingsModel): Either<ServiceError, Unit> {
        return repository.updateSettings(model)
                .mapLeft(::toServiceError)
    }

    fun deleteRecord(accountId: UUID, transactionId: UUID): Either<ServiceError, Unit> {
        return repository.deleteRecord(accountId, transactionId)
                .mapLeft(::toServiceError)
    }

    fun editRecord(model: TransactionModel): Either<ServiceError, Unit> {
        return repository.updateRecord(model)
                .mapLeft(::toServiceError)
    }

    fun createRecord(model: TransactionModel): Either<ServiceError, UUID> {
        val createRecord = repository.createRecord(model)
                .mapLeft(::toServiceError)

        val createSettings: (UUID) -> Either<ServiceError, TransactionSettingsModel> = {id ->
            val settingsModel = TransactionSettingsModel(transactionId = id, preferredPrice = 0.0, groupSelected = true, legClosed = false)
            repository.createSettings(id, settingsModel)
                    .mapLeft(::toServiceError)
                    .map { settingsModel }
        }

        return createRecord
                .flatMap(createSettings)
                .map { it.transactionId }
    }

    fun deleteSettings(transactionId: UUID): Either<ServiceError, Unit> {
        return repository.deleteSettings(transactionId)
                .mapLeft(::toServiceError)
    }

    fun getOptionAndDividendSummaryMatrix(accountId: UUID, portfolioId: UUID): Either<ServiceError, List<SummaryMatrixModel>> {
        return repository.getOptionAndDividendSummaryMatrix(accountId, portfolioId)
                .mapLeft(::toServiceError)
    }

    fun getSharesSummaryMatrix(accountId: UUID, portfolioId: UUID): Either<ServiceError, List<SummaryMatrixModel>> {
        return repository.getSharesSummaryMatrix(accountId, portfolioId)
                .mapLeft(::toServiceError)
    }
}
