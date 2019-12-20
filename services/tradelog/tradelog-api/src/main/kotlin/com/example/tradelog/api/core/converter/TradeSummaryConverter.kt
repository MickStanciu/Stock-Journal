package com.example.tradelog.api.core.converter

import com.example.tradelog.api.core.model.TradeSummaryModel

class TradeSummaryConverter {

    companion object {
//        fun toTradeSummaryResponse(models: List<TradeSummaryModel>): TradeSummaryResponse {
//
//            val items = models.stream()
//                    .map { i -> toTradeSummaryItem(i) }
//                    .collect(Collectors.toList())
//
//            return TradeSummaryResponse.newBuilder()
//                    .addAllItems(items)
//                    .build()
//        }
//
//        private fun toTradeSummaryItem(model: TradeSummaryModel): TradeSummaryItem {
//            return TradeSummaryItem.newBuilder()
//                    .setSymbol(model.symbol)
//                    .setTotal(model.total.toInt())
//                    .setTrades(model.trades)
//                    .build()
//        }

        fun toMap(models: List<TradeSummaryModel>): Map<String, TradeSummaryModel> {
            var summaryModelMap = HashMap<String, TradeSummaryModel>()

            models.forEach {
                if (summaryModelMap.containsKey(it.symbol)) {
                    val storedModel = summaryModelMap[it.symbol]
                    if (storedModel != null) {
                        summaryModelMap.put(it.symbol, TradeSummaryModel(symbol = it.symbol, trades = storedModel.trades +1,
                                total = storedModel.total.add(it.total) ))
                    }
                } else {
                    summaryModelMap[it.symbol] = it
                }
            }

            return summaryModelMap
        }
    }
}
