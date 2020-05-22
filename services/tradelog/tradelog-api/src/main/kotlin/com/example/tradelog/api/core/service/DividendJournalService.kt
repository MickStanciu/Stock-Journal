package com.example.tradelog.api.core.service

import com.example.common.types.Either
import com.example.common.types.Either.Companion.mapError
import com.example.tradelog.api.core.converter.TradeSummaryUtil
import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.repository.DividendJournalRepository
import org.springframework.stereotype.Service

@Service
class DividendJournalService(private val repository: DividendJournalRepository): JournalService<JournalService.TradeLogBusinessError, DividendJournalModel> {

    override fun getSummaries(accountId: String): Map<String, TradeSummaryModel> {
        val modelList = repository.getSummaries(accountId)
        //TODO: HACK
        return TradeSummaryUtil.toMap(models = modelList.valueOrNull()!!)
    }

    override fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): List<DividendJournalModel> {
        //TODO: HACK
        return repository.getAllBySymbol(accountId, portfolioId, symbol).valueOrNull()!!
    }

    override fun createRecord(transactionId: String, model: DividendJournalModel): DividendJournalModel {
        repository.createRecord(transactionId, model)
        val either = repository.getById(model.transactionDetails.accountId, transactionId)
        //TODO: HACK
        return either.valueOrNull()!!
    }

    override fun editRecord(model: DividendJournalModel) {
        //TODO: HACK
        repository.editRecord(model).valueOrNull()
    }

    override fun deleteRecord(transactionId: String) {
        //TODO: HACK
        repository.deleteRecord(transactionId).valueOrNull()
    }

    override fun getById(accountId: String, transactionId: String): Either<JournalService.TradeLogBusinessError, DividendJournalModel> {
        return repository.getById(accountId, transactionId)
                .mapError(::toBusinessError)
    }
}
