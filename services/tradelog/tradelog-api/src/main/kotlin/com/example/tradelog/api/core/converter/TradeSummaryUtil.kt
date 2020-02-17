package com.example.tradelog.api.core.converter

import com.example.tradelog.api.core.model.TradeSummaryModel
import java.math.BigDecimal

class TradeSummaryUtil {

    companion object {
        fun toMap(models: List<TradeSummaryModel>): Map<String, TradeSummaryModel> {
            val summaryModelMap = HashMap<String, TradeSummaryModel>()

            models.stream()
                    .filter { it.legClosed }
                    .forEach {
                        if (summaryModelMap.containsKey(it.symbol)) {
                            val storedModel = summaryModelMap[it.symbol]
                            if (storedModel != null) {
                                summaryModelMap[it.symbol] = TradeSummaryModel(symbol = it.symbol,
                                        trades = storedModel.trades + 1,
                                        total = storedModel.total.add(it.total),
                                        legClosed = storedModel.legClosed)
                            }
                        } else {
                            summaryModelMap[it.symbol] = it
                        }
                    }

            models.stream()
                    .filter { !it.legClosed }
                    .forEach {
                        if (!summaryModelMap.containsKey(it.symbol)) {
                            summaryModelMap[it.symbol] = TradeSummaryModel(symbol = it.symbol,
                                    trades = 0,
                                    total = BigDecimal.ZERO,
                                    legClosed = false)
                        }
                    }

            return summaryModelMap
        }
    }
}
