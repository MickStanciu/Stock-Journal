package com.example.gateway.api.converter;

import com.example.gateway.api.spec.model.ShareDataGWModel;
import com.example.tradelog.api.spec.model.ShareDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class ShareDataConverterTest {

    @Test
    void testConverter() {
        ShareDataModel from = new ShareDataModel(
                "EXEL",
                OffsetDateTime.of(2019, 7, 7, 0, 0, 0, 0, ZoneOffset.UTC),
                "Healthcare", 21.12, 6.4, 10.14, 17.63,
                4.61, 2.08, 1.2,
                BigDecimal.valueOf(30.08),
                BigDecimal.valueOf(22.36)
        );

        ShareDataGWModel to = ShareDataConverter.toShareDataGWModel.apply(from);

        Assertions.assertEquals(from.getSymbol(), to.getSymbol());
        Assertions.assertEquals(from.getBookValue(), to.getBookValue());
        Assertions.assertEquals(from.getCalculatedTarget(), to.getCalculatedTarget());
        Assertions.assertEquals(from.getEps(), to.getEps());
        Assertions.assertEquals(from.getEpsFuture(), to.getEpsFuture());
        Assertions.assertEquals(from.getFinvizTarget(), to.getFinvizTarget());
        Assertions.assertEquals(from.getLastUpdatedOn(), to.getLastUpdatedOn());
        Assertions.assertEquals(from.getMarketCapitalization(), to.getMarketCapitalization());
        Assertions.assertEquals(from.getPeRatio(), to.getPeRatio());
        Assertions.assertEquals(from.getPeRatioFuture(), to.getPeRatioFuture());
        Assertions.assertEquals(from.getPrice(), to.getPrice());
        Assertions.assertEquals(from.getSector(), to.getSector());
    }
}
