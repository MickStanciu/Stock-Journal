package com.example.tradelog.api.core.converter

import com.example.tradelog.api.core.model.TradeSummaryModel

class TradeSummaryUtil {

    companion object {
        fun toMap(models: List<TradeSummaryModel>): Map<String, TradeSummaryModel> {
            val summaryModelMap = HashMap<String, TradeSummaryModel>()

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
