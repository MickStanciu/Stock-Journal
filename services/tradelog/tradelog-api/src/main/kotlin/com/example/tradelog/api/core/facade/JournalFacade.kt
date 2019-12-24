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

/*
        Map<String, TradeSummaryModel> summaryModelMap = new HashMap<>();
        shareSummaries.forEach(summaryModelMap::put);

        optionSummaries.forEach( (k, v) -> {
            if (summaryModelMap.containsKey(v.getSymbol())) {
                TradeSummaryModel storedModel = summaryModelMap.get(v.getSymbol());
                summaryModelMap.put(v.getSymbol(), new TradeSummaryModel(storedModel.getSymbol(), storedModel.getTrades() + v.getTrades(), storedModel.getTotal().add(v.getTotal())));
            } else {
                summaryModelMap.put(v.getSymbol(), v);
            }
        });

        dividendSummaries.forEach( (k, v) -> {
            if (summaryModelMap.containsKey(v.getSymbol())) {
                TradeSummaryModel storedModel = summaryModelMap.get(v.getSymbol());
                summaryModelMap.put(v.getSymbol(), new TradeSummaryModel(storedModel.getSymbol(), storedModel.getTrades() + v.getTrades(), storedModel.getTotal().add(v.getTotal())));
            } else {
                summaryModelMap.put(v.getSymbol(), v);
            }
        });


        return summaryModelMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
 */
        return emptyList()
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
            return shareService.createRecord(id, model)
        }
        return null
    }

    fun editShareRecord(transactionId: String, model: ShareJournalModel): Boolean {
        if (transactionId !== model.transactionDetails.id) {
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

    fun deleteDividendRecord(accountId: String, transactionId: String): Boolean {
        dividendService.getById(accountId, transactionId) ?: return false

        return dividendService.deleteRecord(transactionId)
                && transactionService.deleteSettings(transactionId)
                && transactionService.deleteRecord(accountId, transactionId)
    }

}
