package com.example.gateway.api.core.service

import arrow.core.getOrElse
import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.*
import com.example.gateway.api.core.util.HydrationContext
import com.example.gateway.api.core.util.createSynthetic
import com.example.gateway.api.rest.gateway.StockDataGateway
import com.example.gateway.api.rest.gateway.TradeLogGateway
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.collections.ArrayList

@Service
class TradeLogService(private val tradeLogGateway: TradeLogGateway,
                      private val stockDataGateway: StockDataGateway) {

    fun getAll(supportMetadata: SupportMetadata, symbol: String): TradeLogModel {
        val futureShareList = tradeLogGateway.getAllShareTransactions(supportMetadata, symbol)
        val futureOptionList = tradeLogGateway.getAllOptionTransactions(supportMetadata, symbol)
        val futureDividendList = tradeLogGateway.getAllDividendTransactions(supportMetadata, symbol)

        CompletableFuture.allOf(futureShareList, futureOptionList, futureDividendList).join()
        val stockPrice = stockDataGateway
                .getPrice(symbol)
                .getOrElse { SharePriceModel(
                        symbol = symbol,
                        lastClose = 0.00,
                        lastUpdatedOn = TimeConverter.getOffsetDateTimeNow(),
                        lastFailedOn = null,
                        active = true)
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

    fun getSummary(supportMetadata: SupportMetadata) = tradeLogGateway.getSummary(supportMetadata)

    fun createShareTransaction(supportMetadata: SupportMetadata, model: ShareJournalModel) = tradeLogGateway.createShareTransaction(supportMetadata, model)

    fun editShareTransaction(supportMetadata: SupportMetadata, updateModel: ShareJournalModel) = tradeLogGateway.editShareTransaction(supportMetadata, updateModel)

    fun deleteShareTransaction(supportMetadata: SupportMetadata, transactionId: String) = tradeLogGateway.deleteShareTransaction(supportMetadata, transactionId)

    fun createOptionTransaction(supportMetadata: SupportMetadata, model: OptionJournalModel) = tradeLogGateway.createOptionTransaction(supportMetadata, model)

    fun editOptionTransaction(supportMetadata: SupportMetadata, updateModel: OptionJournalModel) = tradeLogGateway.editOptionTransaction(supportMetadata, updateModel)

    fun deleteOptionTransaction(supportMetadata: SupportMetadata, transactionId: String) = tradeLogGateway.deleteOptionTransaction(supportMetadata, transactionId)

    fun createDividendTransaction(supportMetadata: SupportMetadata, model: DividendJournalModel) = tradeLogGateway.createDividendTransaction(supportMetadata, model)

    fun deleteDividendTransaction(supportMetadata: SupportMetadata, transactionId: String) = tradeLogGateway.deleteDividendTransaction(supportMetadata, transactionId)

    fun getDefaultPortfolio(accountId: UUID) = tradeLogGateway.getDefaultPortfolio(accountId)
}