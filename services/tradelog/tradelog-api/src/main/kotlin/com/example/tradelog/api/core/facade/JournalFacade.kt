package com.example.tradelog.api.core.facade

import com.example.tradelog.api.core.model.*
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

    fun updateSettings(toModel: TransactionSettingsModel): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getAllShareTradesBySymbol(accountId: String, symbol: String): List<ShareJournalModel> {
        TODO("not implemented") //To change body of created functions use File | Sett
    }

    fun createShareRecord(toModel: ShareJournalModel): ShareJournalModel? {
        TODO("not implemented") //To change body of created functions use File | Sett
    }

    fun editShareRecord(transactionId: String, dto: ShareJournalModel): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteShareRecord(accountId: String, transactionId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getAllOptionTradesBySymbol(accountId: String, symbol: String): List<OptionJournalModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createOptionRecord(toModel: OptionJournalModel): OptionJournalModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteOptionRecord(accountId: String, transactionId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getAllDividendTradesBySymbol(accountId: String, symbol: String): List<DividendJournalModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createDividendRecord(toModel: DividendJournalModel): OptionJournalModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteDividendRecord(accountId: String, transactionId: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
