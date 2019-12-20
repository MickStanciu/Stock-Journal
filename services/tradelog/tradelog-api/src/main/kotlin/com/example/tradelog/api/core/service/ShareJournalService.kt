package com.example.tradelog.api.core.service

import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.db.repository.ShareJournalRepository
import org.springframework.stereotype.Service

@Service
class ShareJournalService(private val shareJournalRepository: ShareJournalRepository) {

    fun getSummaries(accountId: String): Map<String, TradeSummaryModel> {
        return shareJournalRepository.getSummaries(accountId)
    }
}
