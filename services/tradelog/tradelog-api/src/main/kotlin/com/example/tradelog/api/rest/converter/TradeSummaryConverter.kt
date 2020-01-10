package com.example.tradelog.api.rest.converter

import java.util.stream.Collectors
import com.example.tradelog.api.core.model.TradeSummaryModel as TLTradeSummaryModel
import com.example.tradelog.api.spec.model.TradeSummaryItem as TLTradeSummaryItem
import com.example.tradelog.api.spec.model.TradeSummaryResponse as TLTradeSummaryResponse

class TradeSummaryConverter {

    companion object {
        fun toTradeSummaryResponse(models: List<TLTradeSummaryModel>): TLTradeSummaryResponse {

            val items = models.stream()
                    .map { i -> toTradeSummaryItem(i) }
                    .collect(Collectors.toList())

            return TLTradeSummaryResponse.newBuilder()
                    .addAllItems(items)
                    .build()
        }

        private fun toTradeSummaryItem(model: TLTradeSummaryModel): TLTradeSummaryItem {
            return TLTradeSummaryItem.newBuilder()
                    .setSymbol(model.symbol)
                    .setTotal(model.total.toDouble())
                    .setTrades(model.trades)
                    .build()
        }
    }

}