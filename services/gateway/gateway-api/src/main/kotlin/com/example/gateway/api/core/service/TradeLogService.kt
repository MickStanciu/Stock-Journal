package com.example.gateway.api.core.service

import com.example.gateway.api.core.model.*
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

    fun deleteShareTransaction(accountId: String, transactionId: String) {
        tradeLogGateway.deleteShareTransaction(accountId, transactionId)
    }

    fun createOptionTransaction(accountId: String, model: OptionJournalModel): OptionJournalModel? {
        return tradeLogGateway.createOptionTransaction(accountId, model)
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