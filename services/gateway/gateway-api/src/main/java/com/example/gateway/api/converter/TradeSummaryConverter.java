package com.example.gateway.api.converter;

import com.example.gateway.api.model.TradeSummaryGWModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;

import java.util.function.Function;

public class TradeSummaryConverter {

    public static Function<TradeSummaryModel, TradeSummaryGWModel> toTradeSummaryGWModel = model ->
            TradeSummaryGWModel.builder()
                    .withSymbol(model.getSymbol())
                    .withTotal(model.getTotal())
                    .withTrades(model.getTrades())
                    .build();
}
