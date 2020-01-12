package com.example.tradelog.api.core.facade

import com.example.tradelog.api.core.model.*
import com.example.tradelog.api.core.service.DividendJournalService
import com.example.tradelog.api.core.service.OptionJournalService
import com.example.tradelog.api.core.service.ShareJournalService
import com.example.tradelog.api.core.service.TransactionService
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class JournalFacade(private val transactionService: TransactionService,
                    private val shareService: ShareJournalService,
                    private val optionService: OptionJournalService,
                    private val dividendService: DividendJournalService) {

    fun getAllTradedSymbols(accountId: String): List<String> {
        return transactionService.getAllTradedSymbols(accountId)
    }

    fun getSummary(accountId: String): List<TradeSummaryModel> {
        //TODO: parallel
        val shareSummaries = shareService.getSummaries(accountId)
        val optionSummaries = optionService.getSummaries(accountId)
        val dividendSummaries = dividendService.getSummaries(accountId)

        val summaryModelMap: HashMap<String, TradeSummaryModel> = HashMap()

        shareSummaries
                .forEach {
                    if (it.value.legClosed) {
                        summaryModelMap[it.key] = it.value
                    }
                }

        optionSummaries.forEach {
            if (summaryModelMap.containsKey(it.key) && it.value.legClosed) {
                val storedModel: TradeSummaryModel = summaryModelMap[it.key]!!

                summaryModelMap[it.key] = TradeSummaryModel(symbol = it.key,
                        trades = storedModel.trades + it.value.trades,
                        total = storedModel.total.add(it.value.total),
                        legClosed = storedModel.legClosed)
            } else {
                if (it.value.legClosed) {
                    summaryModelMap[it.key] = it.value
                }
            }
        }

        dividendSummaries.forEach {
            if (summaryModelMap.containsKey(it.key) && it.value.legClosed) {
                val storedModel: TradeSummaryModel = summaryModelMap[it.key]!!

                summaryModelMap[it.key] = TradeSummaryModel(symbol = it.key,
                        trades = storedModel.trades + it.value.trades,
                        total = storedModel.total.add(it.value.total),
                        legClosed = storedModel.legClosed)
            } else {
                if (it.value.legClosed) {
                    summaryModelMap[it.key] = it.value
                }
            }
        }

        return summaryModelMap.entries.stream()
                .sorted(compareBy { it.key })
                .map { it.value }
                .collect(Collectors.toList())
    }

    fun updateSettings(model: TransactionSettingsModel): Boolean {
        return transactionService.updateSettings(model)
    }

    fun getAllShareTradesBySymbol(accountId: String, symbol: String): List<ShareJournalModel> {
        return shareService.getAllBySymbol(accountId, symbol)
    }

    fun createShareRecord(model: ShareJournalModel): ShareJournalModel? {
        val id = transactionService.createRecord(model.transactionDetails)
        if (id != null) {
            return shareService.createRecord(transactionId = id, model = model)
        }
        return null
    }

    fun editShareRecord(transactionId: String, model: ShareJournalModel): Boolean {
        if (transactionId != model.transactionDetails.id) {
            return false
        }

        return transactionService.editRecord(model.transactionDetails)
                && shareService.editRecord(model)
    }

    fun deleteShareRecord(accountId: String, transactionId: String): Boolean {
        shareService.getById(accountId, transactionId) ?: return false

        return shareService.deleteRecord(transactionId)
                && transactionService.deleteSettings(transactionId)
                && transactionService.deleteRecord(accountId, transactionId)
    }

    fun getAllOptionTradesBySymbol(accountId: String, symbol: String): List<OptionJournalModel> {
        return optionService.getAllBySymbol(accountId, symbol)
    }

    fun createOptionRecord(model: OptionJournalModel): OptionJournalModel? {
        val id = transactionService.createRecord(model.transactionDetails)
        if (id != null) {
            return optionService.createRecord(id, model)
        }
        return null
    }

    fun editOptionRecord(transactionId: String, model: OptionJournalModel): Boolean {
        if (transactionId != model.transactionDetails.id) {
            return false
        }

        return transactionService.editRecord(model.transactionDetails)
                && optionService.editRecord(model)
    }

    fun deleteOptionRecord(accountId: String, transactionId: String): Boolean {
        optionService.getById(accountId, transactionId) ?: return false

        return optionService.deleteRecord(transactionId)
                && transactionService.deleteSettings(transactionId)
                && transactionService.deleteRecord(accountId, transactionId)
    }

    fun getAllDividendTradesBySymbol(accountId: String, symbol: String): List<DividendJournalModel> {
        return dividendService.getAllBySymbol(accountId, symbol)
    }

    fun createDividendRecord(model: DividendJournalModel): DividendJournalModel? {
        val id = transactionService.createRecord(model.transactionDetails)
        if (id != null) {
            return dividendService.createRecord(id, model)
        }
        return null
    }

    fun editDividendRecord(transactionId: String, model: DividendJournalModel): Boolean {
        if (transactionId != model.transactionDetails.id) {
            return false
        }

        return transactionService.editRecord(model.transactionDetails)
                && dividendService.editRecord(model)
    }

    fun deleteDividendRecord(accountId: String, transactionId: String): Boolean {
        dividendService.getById(accountId, transactionId) ?: return false

        return dividendService.deleteRecord(transactionId)
                && transactionService.deleteSettings(transactionId)
                && transactionService.deleteRecord(accountId, transactionId)
    }

}
