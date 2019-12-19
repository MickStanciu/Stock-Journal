package com.example.tradelog.api.converter;

import com.example.tradelog.api.core.model.TradeSummaryModel;

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

                summaryModelMap.put(m.getSymbol(), new TradeSummaryModel(storedModel.getSymbol(), storedModel.getTrades() + 1, storedModel.getTotal().add(m.getTotal())));
            } else {
                summaryModelMap.put(m.getSymbol(), m);
            }
        });

        return summaryModelMap;
    };
}
