package com.example.gateway.api.core.service

import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.TradeLogModel
import com.example.gateway.api.core.model.TradeSummaryModel
import com.example.gateway.api.rest.gateway.TradeLogGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class TradeLogService(private val tradeLogGateway: TradeLogGateway) {

//    fun getAllTradedSymbols(accountId: String): List<String> {
//        return tradeLogGateway.getAllTradedSymbols(accountId)
//    }

    fun getAll(accountId: String, symbol: String): TradeLogModel {
        val futureShareList = tradeLogGateway.getAllShareTransactions(accountId, symbol)
        val futureOptionList = tradeLogGateway.getAllOptionTransactions(accountId, symbol)
        val futureDividendList = tradeLogGateway.getAllDividendTransactions(accountId, symbol)

        CompletableFuture.allOf(futureShareList, futureOptionList, futureDividendList).join()

        return TradeLogModel(
                shareList = futureShareList.get(),
                optionList = futureOptionList.get(),
                dividendList = futureDividendList.get()
        )
    }

    fun getSummary(accountId: String): List<TradeSummaryModel> {
        return tradeLogGateway.getSummary(accountId)
    }

    fun createShareTransaction(accountId: String, model: ShareJournalModel): ShareJournalModel? {
        return tradeLogGateway.createShareTransaction(accountId, model)
    }
}