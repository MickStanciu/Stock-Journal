package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.converter.TradeSummaryConverter
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.repository.ShareJournalRepository
import org.springframework.stereotype.Service

@Service
class ShareJournalService(private val shareJournalRepository: ShareJournalRepository) {

    fun getSummaries(accountId: String): Map<String, TradeSummaryModel> {
        val modelList = shareJournalRepository.getSummaries(accountId)
        return TradeSummaryConverter.toMap(models = modelList)
    }
}

/*
    public Map<String, TradeSummaryModel> getSummaries(String accountId) {
    List<TradeSummaryModel> modelList = sharesJournalRepository.getSummaries(accountId);
    return TradeSummaryListConverter.toMap.apply(modelList);
}
 */
