package com.example.gateway.api.core.util

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.core.model.ShareAggregator
import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.TransactionType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.abs

fun createSynthetic(models: List<ShareJournalModel>): List<ShareJournalModel> {
    val stockMap = HashMap<String, ShareAggregator>()

    models.stream()
            .filter { it.transactionType == TransactionType.SHARE && it.groupSelected && !it.legClosed}
            .forEach {
                val quantity = if (ActionType.BUY === it.action) {
                    it.quantity
                } else {
                    it.quantity * -1
                }

                val aggregator = if (stockMap.containsKey(it.symbol)) {
                    stockMap[it.symbol]!!
                } else {
                    ShareAggregator(symbol = it.symbol,
                            actualPrice = it.actualPrice,
                            preferredPrice = it.preferredPrice)
                }
                aggregator.addQuantityAndPrice(quantity, it.price)
                stockMap[it.symbol] = aggregator
            }

    val synthetics = ArrayList<ShareJournalModel>()
    stockMap.forEach {(symbol, aggregator) ->
        if (aggregator.outstandingShares != 0) {
            val averageBoughPrice = aggregator.averageBoughtPrice
            var syntheticActionType = ActionType.SELL
            if (aggregator.outstandingShares < 0) {
                syntheticActionType = ActionType.BUY
            }

            synthetics.add(ShareJournalModel(
                    transactionId = UUID.randomUUID(),
                    accountId = UUID.randomUUID(),
                    portfolioId = UUID.randomUUID(),
                    symbol = symbol,
                    date = TimeConverter.nextYear(),
                    price = averageBoughPrice,
                    preferredPrice = aggregator.preferredPrice,
                    actualPrice = aggregator.actualPrice,
                    quantity = abs(aggregator.outstandingShares),
                    brokerFees = 0.0,
                    groupSelected = true,
                    legClosed = true,
                    transactionType = TransactionType.SYNTHETIC_SHARE,
                    action = syntheticActionType
            ))

        }
    }

    return synthetics
}