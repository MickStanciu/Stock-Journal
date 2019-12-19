package com.example.tradelog.api.core.facade

import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.core.service.DividendJournalService
import com.example.tradelog.api.core.service.OptionJournalService
import com.example.tradelog.api.core.service.ShareJournalService
import com.example.tradelog.api.core.service.TransactionService
import org.springframework.stereotype.Service

@Service
class JournalFacade(private val transactionService: TransactionService,
                    private val shareService: ShareJournalService,
                    private val optionService: OptionJournalService,
                    private val dividendService: DividendJournalService) {

    fun getAllTradedSymbols(accountId: String): List<String> {
        return transactionService.getAllTradedSymbols(accountId)
    }

    fun getSummary(accountId: String): List<TradeSummaryModel> {
        val shareSummaries = shareService.getSummaries(accountId)
        val optionSummaries = optionService.getSummaries(accountId)
        val dividendSummaries = dividendService.getSummaries(accountId)

        val summaryModelMap: HashMap<String, TradeSummaryModel>



//        return summaryModelMap.entries.stream()
//                .sorted(java.util.Map.Entry.comparingByKey())
//                .map(Function<Map.Entry<String, TradeSummaryModel>, TradeSummaryModel> { java.util.Map.Entry.value })
//                .collect(Collectors.toList())
        return emptyList()
    }


}