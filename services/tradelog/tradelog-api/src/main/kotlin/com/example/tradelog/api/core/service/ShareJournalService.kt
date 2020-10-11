package com.example.tradelog.api.core.service

import arrow.core.Either
import arrow.core.flatMap
import com.example.common.service.ServiceError
import com.example.common.service.toServiceError
import com.example.tradelog.api.core.converter.TradeSummaryUtil
import com.example.tradelog.api.core.model.ShareJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.repository.ShareJournalRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShareJournalService(private val repository: ShareJournalRepository): JournalService<ShareJournalModel> {

    override fun getSummaries(accountId: UUID): Either<ServiceError, Map<String, TradeSummaryModel>> {
        return repository.getSummaries(accountId)
                .mapLeft(::toServiceError)
                .map { TradeSummaryUtil.toMap(it) }
    }

    override fun getAllBySymbol(accountId: UUID, portfolioId: UUID, symbol: String): Either<ServiceError, List<ShareJournalModel>> {
        return repository.getAllBySymbol(accountId, portfolioId, symbol)
                .mapLeft(::toServiceError)
    }

    override fun createRecord(transactionId: UUID, model: ShareJournalModel): Either<ServiceError, ShareJournalModel> {
        val createRecord = repository.createRecord(transactionId, model)
                .mapLeft(::toServiceError)

        val readRecord: (Unit) -> Either<ServiceError, ShareJournalModel> = {
            repository.getById(model.transactionDetails.accountId, transactionId)
                    .mapLeft(::toServiceError)
        }

        return createRecord
                .flatMap(readRecord)
    }

    override fun editRecord(model: ShareJournalModel): Either<ServiceError, Unit> {
        return repository.editRecord(model)
                .mapLeft(::toServiceError)
    }

    override fun deleteRecord(transactionId: UUID): Either<ServiceError, Unit> {
        return repository.deleteRecord(transactionId)
                .mapLeft(::toServiceError)
    }

    override fun getById(accountId: UUID, transactionId: UUID): Either<ServiceError, ShareJournalModel> {
        return repository.getById(accountId, transactionId)
                .mapLeft(::toServiceError)
    }
}

