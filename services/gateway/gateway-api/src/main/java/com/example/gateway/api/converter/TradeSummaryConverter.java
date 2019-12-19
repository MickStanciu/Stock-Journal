package com.example.gateway.api.converter;

import com.example.gateway.api.spec.model.TradeSummaryGWModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;

import java.math.BigDecimal;
import java.util.function.Function;

public class TradeSummaryConverter {

    public static Function<TradeSummaryModel, TradeSummaryGWModel> toTradeSummaryGWModel = model ->
            TradeSummaryGWModel.builder()
                    .withSymbol(model.getSymbol())
                    .withTotal(BigDecimal.valueOf(model.getTotal()))
                    .withTrades(model.getTrades())
                    .build();
}
