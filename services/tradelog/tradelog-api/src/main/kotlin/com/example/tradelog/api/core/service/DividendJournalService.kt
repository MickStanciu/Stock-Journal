package com.example.tradelog.api.core.service

import arrow.core.Either
import arrow.core.flatMap
import com.example.common.service.ServiceError
import com.example.common.service.toServiceError
import com.example.tradelog.api.core.converter.TradeSummaryUtil
import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.repository.DividendJournalRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DividendJournalService(private val repository: DividendJournalRepository): JournalService<DividendJournalModel> {

    override fun getSummaries(accountId: UUID): Either<ServiceError, Map<String, TradeSummaryModel>> {
        return repository.getSummaries(accountId)
                .mapLeft(::toServiceError)
                .map { TradeSummaryUtil.toMap(it) }
    }

    override fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): Either<ServiceError, List<DividendJournalModel>> {
        return repository.getAllBySymbol(accountId, portfolioId, symbol)
                .mapLeft(::toServiceError)
    }

    override fun createRecord(transactionId: UUID, model: DividendJournalModel): Either<ServiceError, DividendJournalModel> {
        val createRecord = repository.createRecord(transactionId, model)
                .mapLeft(::toServiceError)

        val readRecord: (Unit) -> Either<ServiceError, DividendJournalModel> = {
            repository.getById(model.transactionDetails.accountId, transactionId)
                    .mapLeft(::toServiceError)
        }

        return createRecord
                .flatMap(readRecord)
    }

    override fun editRecord(model: DividendJournalModel): Either<ServiceError, Unit> {
        return repository.editRecord(model)
                .mapLeft(::toServiceError)
    }

    override fun deleteRecord(transactionId: UUID): Either<ServiceError, Unit> {
        return repository.deleteRecord(transactionId)
                .mapLeft(::toServiceError)
    }

    override fun getById(accountId: UUID, transactionId: UUID): Either<ServiceError, DividendJournalModel> {
        return repository.getById(accountId, transactionId)
                .mapLeft(::toServiceError)
    }
}
