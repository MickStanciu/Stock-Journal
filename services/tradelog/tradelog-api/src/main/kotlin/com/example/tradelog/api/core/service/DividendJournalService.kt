package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.converter.TradeSummaryUtil
import com.example.tradelog.api.core.model.DividendJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.repository.DividendJournalRepository
import org.springframework.stereotype.Service

@Service
class DividendJournalService(private val repository: DividendJournalRepository) : JournalService<DividendJournalModel> {

    override fun getSummaries(accountId: String): Map<String, TradeSummaryModel> {
        val modelList = repository.getSummaries(accountId)
        return TradeSummaryUtil.toMap(models = modelList)
    }

    override fun getAllBySymbol(accountId: String, portfolioId: String, symbol: String): List<DividendJournalModel> {
        return repository.getAllBySymbol(accountId, portfolioId, symbol)
    }

    override fun createRecord(transactionId: String, model: DividendJournalModel): DividendJournalModel? {
        repository.createRecord(transactionId, model)
        return repository.getById(model.transactionDetails.accountId, transactionId)
    }

    override fun editRecord(model: DividendJournalModel): Boolean {
        return repository.editRecord(model)
    }

    override fun deleteRecord(transactionId: String): Boolean {
        return repository.deleteRecord(transactionId)
    }

    override fun getById(accountId: String, transactionId: String): DividendJournalModel? {
        return repository.getById(accountId, transactionId)
    }
}
