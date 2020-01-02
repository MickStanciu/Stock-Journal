package com.example.gateway.api.converter;

import com.example.tradelog.api.spec.model.TradeSummaryItem;

import java.math.BigDecimal;
import java.util.function.Function;

public class TradeSummaryConverter {

    public static Function<TradeSummaryItem, TradeSummaryGWModel> toTradeSummaryGWModel = model ->
            TradeSummaryGWModel.builder()
                    .withSymbol(model.getSymbol())
                    .withTotal(BigDecimal.valueOf(model.getTotal()))
                    .withTrades(model.getTrades())
                    .build();
}
