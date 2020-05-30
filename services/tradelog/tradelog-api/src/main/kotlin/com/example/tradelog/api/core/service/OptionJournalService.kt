package com.example.tradelog.api.core.service

import com.example.common.service.ServiceError
import com.example.common.service.toServiceError
import com.example.common.types.Either
import com.example.common.types.Either.Companion.bind
import com.example.tradelog.api.core.converter.TradeSummaryUtil
import com.example.tradelog.api.core.model.OptionJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.repository.OptionJournalRepository
import org.springframework.stereotype.Service

@Service
class OptionJournalService(private val repository: OptionJournalRepository): JournalService<OptionJournalModel> {

    override fun getSummaries(accountId: String): Either<ServiceError, Map<String, TradeSummaryModel>> {
        return repository.getSummaries(accountId)
                .mapLeft(::toServiceError)
                .mapRight { TradeSummaryUtil.toMap(it) }
    }

    override fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): Either<ServiceError, List<OptionJournalModel>> {
        return repository.getAllBySymbol(accountId, portfolioId, symbol)
                .mapLeft(::toServiceError)
    }

    override fun createRecord(transactionId: String, model: OptionJournalModel): Either<ServiceError, OptionJournalModel> {
        val createRecord = repository.createRecord(transactionId, model)
                .mapLeft(::toServiceError)

        val readRecord: (Unit) -> Either<ServiceError, OptionJournalModel> = {
            repository.getById(model.transactionDetails.accountId, transactionId)
                    .mapLeft(::toServiceError)
        }

        return createRecord
                .bind(readRecord)
    }

    override fun editRecord(model: OptionJournalModel): Either<ServiceError, Unit> {
        return repository.editRecord(model)
                .mapLeft(::toServiceError)
    }

    override fun deleteRecord(transactionId: String): Either<ServiceError, Unit> {
        return repository.deleteRecord(transactionId)
                .mapLeft(::toServiceError)
    }

    override fun getById(accountId: String, transactionId: String): Either<ServiceError, OptionJournalModel> {
        return repository.getById(accountId, transactionId)
                .mapLeft(::toServiceError)
    }
}
