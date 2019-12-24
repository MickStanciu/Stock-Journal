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

    /**
     * Returns all shares per symbol including a calculated synthetic one
     * @param accountId -
     * @param symbol -
     * @return list
     */
    override fun getAllBySymbol(accountId: String, symbol: String): List<ShareJournalModel> {
        val modelList = repository.getAllBySymbol(accountId, symbol)
        TODO("not implemented")//shareJournalModelList.addAll(SyntheticSharesGenerator.createSynthetic.apply(shareJournalModelList));
        return modelList
    }

    override fun createRecord(transactionId: String, model: ShareJournalModel): ShareJournalModel {
        repository.createRecord(transactionId, model)
        return repository.getById(transactionId)
    }

    override fun editRecord(model: ShareJournalModel): Boolean {
        return repository.editRecord(model)
    }

    override fun deleteRecord(transactionId: String): Boolean {
        return repository.deleteRecord(transactionId)
    }

    override fun getById(accountId: String, transactionId: String): ShareJournalModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

