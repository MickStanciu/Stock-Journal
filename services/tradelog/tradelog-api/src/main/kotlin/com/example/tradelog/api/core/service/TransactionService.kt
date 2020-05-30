package com.example.tradelog.api.core.service

import com.example.common.service.ServiceError
import com.example.common.service.toServiceError
import com.example.common.types.Either
import com.example.common.types.Either.Companion.bind
import com.example.tradelog.api.core.model.SummaryMatrixModel
import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.db.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionService(private val repository: TransactionRepository) {

    fun getAllTradedSymbols(accountId: String, portfolioId: String): Either<ServiceError, List<String>> {
        return repository.getUniqueSymbols(accountId, portfolioId)
                .mapLeft(::toServiceError)
    }

    fun getActiveSymbols(): Either<ServiceError, List<String>> {
        return repository.getActiveSymbols()
                .mapLeft(::toServiceError)
    }

    fun updateSettings(model: TransactionSettingsModel): Either<ServiceError, Unit> {
        return repository.updateSettings(model)
                .mapLeft(::toServiceError)
    }

    fun deleteRecord(accountId: String, transactionId: String): Either<ServiceError, Unit> {
        return repository.deleteRecord(accountId, transactionId)
                .mapLeft(::toServiceError)
    }

    fun editRecord(model: TransactionModel): Either<ServiceError, Unit> {
        return repository.updateRecord(model)
                .mapLeft(::toServiceError)
    }

    fun createRecord(model: TransactionModel): Either<ServiceError, String> {
        val createRecord = repository.createRecord(model)
                .mapLeft(::toServiceError)

        val createSettings: (String) -> Either<ServiceError, TransactionSettingsModel> = {id ->
            val settingsModel = TransactionSettingsModel(transactionId = id, preferredPrice = 0.0, groupSelected = true, legClosed = false)
            repository.createSettings(id, settingsModel)
                    .mapLeft(::toServiceError)
                    .mapRight { settingsModel }
        }

        return createRecord
                .bind(createSettings)
                .mapRight { it.transactionId }
    }

    fun deleteSettings(transactionId: String): Either<ServiceError, Unit> {
        return repository.deleteSettings(transactionId)
                .mapLeft(::toServiceError)
    }

    fun getSummaryMatrix(accountId: String, portfolioId: String): Either<ServiceError, List<SummaryMatrixModel>> {
        return repository.getSummaryMatrix(accountId, portfolioId)
                .mapLeft(::toServiceError)
    }
}
