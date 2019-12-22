package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.spec.model.TradeSummaryItem
import com.example.tradelog.api.spec.model.TradeSummaryResponse
import java.util.stream.Collectors

class TradeSummaryConverter {

    companion object {
        fun toTradeSummaryResponse(models: List<TradeSummaryModel>): TradeSummaryResponse {

            val items = models.stream()
                    .map { i -> toTradeSummaryItem(i) }
                    .collect(Collectors.toList())

            return TradeSummaryResponse.newBuilder()
                    .addAllItems(items)
                    .build()
        }

        private fun toTradeSummaryItem(model: TradeSummaryModel): TradeSummaryItem {
            return TradeSummaryItem.newBuilder()
                    .setSymbol(model.symbol)
                    .setTotal(model.total.toInt())
                    .setTrades(model.trades)
                    .build()
        }
    }

}