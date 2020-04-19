package com.example.tradelog.api.core.facade

import com.example.tradelog.api.core.model.*
import com.example.tradelog.api.core.service.*
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class JournalFacade(private val transactionService: TransactionService,
                    private val shareService: ShareJournalService,
                    private val optionService: OptionJournalService,
                    private val dividendService: DividendJournalService,
                    private val portfolioService: PortfolioService
) {

    fun getAllTradedSymbols(accountId: String, portfolioId: String): List<String> {
        return transactionService.getAllTradedSymbols(accountId, portfolioId)
    }

    fun getActiveSymbols(): List<String> {
        return transactionService.getActiveSymbols()
    }

    fun getSummary(accountId: String): List<TradeSummaryModel> {
        //TODO: parallel
        val shareSummaries = shareService.getSummaries(accountId)
        val optionSummaries = optionService.getSummaries(accountId)
        val dividendSummaries = dividendService.getSummaries(accountId)

        val summaryModelMap: HashMap<String, TradeSummaryModel> = HashMap()

        shareSummaries.forEach { summaryModelMap[it.key] = it.value }

        optionSummaries.forEach {
            if (summaryModelMap.containsKey(it.key)) {
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
            if (summaryModelMap.containsKey(it.key)) {
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

    fun getSummaryMatrix(accountId: String, portfolioId: String): List<SummaryMatrixModel> {
        val models = transactionService.getSummaryMatrix(accountId, portfolioId)
        val groupedMap = groupByYearAndMonth(models)

        return groupedMap.entries.stream()
                .map { SummaryMatrixModel(year = it.key.first, month = it.key.second, total = it.value) }
                .collect(Collectors.toList())
    }

    private fun groupByYearAndMonth(models: List<SummaryMatrixModel>): Map<Pair<Int, Int>, Double> {
        val map = HashMap<Pair<Int, Int>, Double>()
        models.forEach {
            if (map.containsKey(Pair(it.year, it.month))) {
                val storedTotal = map.getValue(Pair(it.year, it.month))
                map[Pair(it.year, it.month)] = storedTotal.plus(it.total)
            } else {
                map[Pair(it.year, it.month)] = it.total
            }
        }

        return map
    }

    fun updateSettings(model: TransactionSettingsModel): Boolean {
        return transactionService.updateSettings(model)
    }

    fun getAllShareTradesBySymbol(accountId: String, portfolioId: String, symbol: String): List<ShareJournalModel> {
        return shareService.getAllBySymbol(accountId, portfolioId, symbol)
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

    fun getAllOptionTradesBySymbol(accountId: String, portfolioId: String, symbol: String): List<OptionJournalModel> {
        return optionService.getAllBySymbol(accountId, portfolioId, symbol)
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

    fun getAllDividendTradesBySymbol(accountId: String, portfolioId: String, symbol: String): List<DividendJournalModel> {
        return dividendService.getAllBySymbol(accountId, portfolioId, symbol)
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

    fun getPortfolios(accountId: String): List<PortfolioModel> {
        return portfolioService.getPortfolios(accountId)
    }

    fun getDefaultPortfolio(accountId: String): PortfolioModel? {
        return portfolioService.getDefaultPortfolio(accountId)
    }

}
