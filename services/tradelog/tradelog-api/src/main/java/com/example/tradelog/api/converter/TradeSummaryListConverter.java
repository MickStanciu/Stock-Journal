package com.example.tradelog.api.converter;

import com.example.tradelog.api.spec.model.TradeSummaryModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TradeSummaryListConverter {

    public static Function<List<TradeSummaryModel>, Map<String, TradeSummaryModel>> toMap = modelList -> {
        Map<String, TradeSummaryModel> summaryModelMap = new HashMap<>();

        modelList.forEach(m -> {
            if (summaryModelMap.containsKey(m.getSymbol())) {
                TradeSummaryModel storedModel = summaryModelMap.get(m.getSymbol());
                summaryModelMap.put(m.getSymbol(), TradeSummaryModel.builder()
                        .withSymbol(storedModel.getSymbol())
                        .withTrades(storedModel.getTrades() + 1)
                        .withTotal(storedModel.getTotal().add(m.getTotal()))
                        .build());
            } else {
                summaryModelMap.put(m.getSymbol(), m);
            }
        });

        return summaryModelMap;
    };
}
