package com.example.gateway.api.converter;

import com.example.gateway.api.spec.model.ShareDataGWModel;
import com.example.stockdata.api.spec.model.ShareDataResponse;
import com.example.tradelog.api.spec.model.ShareDataModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class ShareDataConverterTest {

    @Test
    void testConverter() {
        OffsetDateTime time = OffsetDateTime.of(2019, 7, 7, 0, 0, 0, 0, ZoneOffset.UTC);

        ShareDataResponse from = ShareDataResponse.newBuilder()
                .setSymbol("EXEL")
                .setLastUpdatedOn(time.toString())
                .setPrice(30.08)
                .build();

        ShareDataGWModel to = ShareDataConverter.toShareDataGWModel.apply(from);

        Assertions.assertEquals(from.getSymbol(), to.getSymbol());
//        Assertions.assertEquals(from.getBookValue(), to.getBookValue());
//        Assertions.assertEquals(from.getCalculatedTarget(), to.getCalculatedTarget());
//        Assertions.assertEquals(from.getEps(), to.getEps());
//        Assertions.assertEquals(from.getEpsFuture(), to.getEpsFuture());
//        Assertions.assertEquals(from.getFinvizTarget(), to.getFinvizTarget());
        Assertions.assertEquals(OffsetDateTime.parse(from.getLastUpdatedOn()), to.getLastUpdatedOn());
//        Assertions.assertEquals(from.getMarketCapitalization(), to.getMarketCapitalization());
//        Assertions.assertEquals(from.getPeRatio(), to.getPeRatio());
//        Assertions.assertEquals(from.getPeRatioFuture(), to.getPeRatioFuture());
        Assertions.assertEquals(from.getPrice(), to.getPrice());
//        Assertions.assertEquals(from.getSector(), to.getSector());
    }
}
