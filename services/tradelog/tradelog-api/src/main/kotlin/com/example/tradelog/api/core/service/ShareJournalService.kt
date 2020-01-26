package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.converter.TradeSummaryUtil
import com.example.tradelog.api.core.model.ShareJournalModel
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.repository.ShareJournalRepository
import org.springframework.stereotype.Service

@Service
class ShareJournalService(private val repository: ShareJournalRepository) : JournalService<ShareJournalModel> {

    override fun getSummaries(accountId: String): Map<String, TradeSummaryModel> {
        val modelList = repository.getSummaries(accountId)
        return TradeSummaryUtil.toMap(models = modelList)
    }

    override fun getAllBySymbol(accountId: String, symbol: String): List<ShareJournalModel> {
        return ArrayList(repository.getAllBySymbol(accountId, symbol))
    }

    override fun createRecord(transactionId: String, model: ShareJournalModel): ShareJournalModel? {
        repository.createRecord(transactionId, model)
        return repository.getById(model.transactionDetails.accountId, transactionId)
    }

    override fun editRecord(model: ShareJournalModel): Boolean {
        return repository.editRecord(model)
    }

    override fun deleteRecord(transactionId: String): Boolean {
        return repository.deleteRecord(transactionId)
    }

    override fun getById(accountId: String, transactionId: String): ShareJournalModel? {
        return repository.getById(accountId, transactionId)
    }
}

