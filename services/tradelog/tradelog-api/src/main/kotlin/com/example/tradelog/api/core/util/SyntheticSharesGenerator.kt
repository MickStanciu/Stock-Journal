package com.example.tradelog.api.core.util

import com.example.common.converter.TimeConverter
import com.example.tradelog.api.core.model.*
import kotlin.math.abs

fun createSynthetic(models: List<ShareJournalModel>): List<ShareJournalModel> {
    val stockMap = HashMap<String, ShareAggregator>()

    models.stream()
            .filter {it.transactionDetails.type == TransactionType.SHARE}
            .filter {!it.transactionDetails.settings.legClosed}
            .forEach {
                val quantity = if (ActionType.BUY === it.action) {
                    it.quantity
                } else {
                    it.quantity * -1
                }

                val aggregator = if (stockMap.containsKey(it.transactionDetails.symbol)) {
                    stockMap[it.transactionDetails.symbol]!!
                } else {
                    ShareAggregator(symbol = it.transactionDetails.symbol,
                            actualPrice = it.actualPrice,
                            preferredPrice = it.transactionDetails.settings.preferredPrice)
                }

                aggregator.addQuantityAndPrice(quantity, it.price)
                stockMap[it.transactionDetails.symbol] = aggregator
            }

    val synthetics = ArrayList<ShareJournalModel>()
    stockMap.forEach {(symbol, aggregator) ->
        if (aggregator.quantity != 0) {
            val averageBoughPrice = aggregator.averageBoughtPrice
            var syntheticActionType = ActionType.SELL
            if (aggregator.quantity < 0) {
                syntheticActionType = ActionType.BUY
            }

            val settingsModel = TransactionSettingsModel(transactionId = "",
                    preferredPrice = aggregator.preferredPrice,
                    groupSelected = true, legClosed = false)

            val transactionModel = TransactionModel(id = "",
                    accountId = "",
                    date = TimeConverter.nextYear(),
                    symbol = symbol,
                    type = TransactionType.SYNTHETIC_SHARE,
                    brokerFees = 0.0,
                    settings = settingsModel)

            synthetics.add(ShareJournalModel(transactionDetails = transactionModel,
                    price = averageBoughPrice,
                    actualPrice = aggregator.actualPrice,
                    quantity = abs(aggregator.quantity),
                    action = syntheticActionType
            ))

        }
    }

    return synthetics
}