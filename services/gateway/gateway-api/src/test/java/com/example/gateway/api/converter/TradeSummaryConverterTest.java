package com.example.gateway.api.converter;

import com.example.gateway.api.model.TradeSummaryGWModel;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TradeSummaryConverterTest {

    @Test
    void testConverter() {
        TradeSummaryModel from = new TradeSummaryModel("XYZ", 5, BigDecimal.valueOf(33.564));
        TradeSummaryGWModel to = TradeSummaryConverter.toTradeSummaryGWModel.apply(from);

        Assertions.assertEquals(from.getSymbol(), to.getSymbol());
        Assertions.assertEquals(from.getTotal(), to.getTotal());
        Assertions.assertEquals(from.getTrades(), to.getTrades());
    }
}