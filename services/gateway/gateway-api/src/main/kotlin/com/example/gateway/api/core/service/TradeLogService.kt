package com.example.gateway.api.core.service

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.*
import com.example.gateway.api.core.util.HydrationContext
import com.example.gateway.api.core.util.createSynthetic
import com.example.gateway.api.rest.gateway.StockDataGateway
import com.example.gateway.api.rest.gateway.TradeLogGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class TradeLogService(private val tradeLogGateway: TradeLogGateway,
                      private val stockDataGateway: StockDataGateway) {

    fun getAll(accountId: String, portfolioId: String, symbol: String): TradeLogModel {
        val futureShareList = tradeLogGateway.getAllShareTransactions(accountId, portfolioId, symbol)
        val futureOptionList = tradeLogGateway.getAllOptionTransactions(accountId, portfolioId, symbol)
        val futureDividendList = tradeLogGateway.getAllDividendTransactions(accountId, portfolioId, symbol)

        CompletableFuture.allOf(futureShareList, futureOptionList, futureDividendList).join()
        var stockPrice = stockDataGateway.getPrice(symbol)
        if (stockPrice == null) {
            stockPrice = SharePriceModel(symbol = symbol, lastClose = 0.00, lastUpdatedOn = TimeConverter.getOffsetDateTimeNow())
        }

        var receivedShareList = ArrayList<ShareJournalModel>()
        if (futureShareList.get().isNotEmpty()) {
            receivedShareList = ArrayList(futureShareList.get() as ArrayList<ShareJournalModel>)
        }

        val hydrationContext = HydrationContext(
                symbol = symbol,
                shareList = receivedShareList,
                priceModel = stockPrice
        )

        val shareList = ArrayList<ShareJournalModel>(hydrationContext.shareList)
        addLastKnownPrice(shareList, hydrationContext)
        generateSynthetic(shareList, hydrationContext)

        return TradeLogModel(
                shareList = shareList,
                optionList = futureOptionList.get(),
                dividendList = futureDividendList.get()
        )
    }

    private fun generateSynthetic(shareList: ArrayList<ShareJournalModel>, hydrationContext: HydrationContext) {
        shareList.addAll(createSynthetic(hydrationContext.shareList))
    }

    private fun addLastKnownPrice(shareList: ArrayList<ShareJournalModel>, hydrationContext: HydrationContext) {
        for ( (index, model) in shareList.withIndex()) {
            shareList[index] = model.copy(actualPrice = hydrationContext.priceModel.lastClose)
        }
    }

    fun getSummary(accountId: String): List<TradeSummaryModel> {
        return tradeLogGateway.getSummary(accountId)
    }

    fun createShareTransaction(accountId: String, model: ShareJournalModel): ShareJournalModel? {
        return tradeLogGateway.createShareTransaction(accountId, model)
    }

    fun editShareTransaction(accountId: String, updateModel: ShareJournalModel) {
        tradeLogGateway.editShareTransaction(accountId, updateModel)
    }

    fun deleteShareTransaction(accountId: String, transactionId: String) {
        tradeLogGateway.deleteShareTransaction(accountId, transactionId)
    }

    fun createOptionTransaction(accountId: String, model: OptionJournalModel): OptionJournalModel? {
        return tradeLogGateway.createOptionTransaction(accountId, model)
    }

    fun editOptionTransaction(accountId: String, updateModel: OptionJournalModel) {
        tradeLogGateway.editOptionTransaction(accountId, updateModel)
    }

    fun deleteOptionTransaction(accountId: String, transactionId: String) {
        tradeLogGateway.deleteOptionTransaction(accountId, transactionId)
    }

    fun createDividendTransaction(accountId: String, model: DividendJournalModel): DividendJournalModel? {
        return tradeLogGateway.createDividendTransaction(accountId, model)
    }

    fun deleteDividendTransaction(accountId: String, transactionId: String) {
        tradeLogGateway.deleteDividendTransaction(accountId, transactionId)
    }
}