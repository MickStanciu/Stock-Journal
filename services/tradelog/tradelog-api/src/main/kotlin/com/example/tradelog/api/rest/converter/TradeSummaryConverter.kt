package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.spec.model.TLTradeSummaryItem
import com.example.tradelog.api.spec.model.TLTradeSummaryResponse
import java.util.stream.Collectors


class TradeSummaryConverter {

    companion object {
        fun toTradeSummaryResponse(models: List<TradeSummaryModel>): TLTradeSummaryResponse {

            val items = models.stream()
                    .map { i -> toTradeSummaryItem(i) }
                    .collect(Collectors.toList())

            return TLTradeSummaryResponse.newBuilder()
                    .addAllItems(items)
                    .build()
        }

        private fun toTradeSummaryItem(model: TradeSummaryModel): TLTradeSummaryItem {
            return TLTradeSummaryItem.newBuilder()
                    .setSymbol(model.symbol)
                    .setTotal(model.total.toDouble())
                    .setTrades(model.trades)
                    .build()
        }
    }

}