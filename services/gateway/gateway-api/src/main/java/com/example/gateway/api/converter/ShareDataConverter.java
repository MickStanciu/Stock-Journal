package com.example.gateway.api.converter;

import com.example.gateway.api.spec.model.ShareDataGWModel;
import com.example.stockdata.api.spec.model.PriceItemResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Function;

public class ShareDataConverter {

    public static Function<PriceItemResponse, ShareDataGWModel> toShareDataGWModel = model ->
            ShareDataGWModel.builder()
                    .withBookValue(0)
                    .withCalculatedTarget(BigDecimal.ZERO)
                    .withEps(0)
                    .withEpsFuture(0)
                    .withFinvizTarget(BigDecimal.ZERO)
                    .withLastUpdatedOn(OffsetDateTime.parse(model.getLastUpdatedOn()))
                    .withMarketCapitalization(0)
                    .withPeRatio(0)
                    .withPeRatioFuture(0)
                    .withPrice(model.getLastClose())
                    .withSector("FAKE")
                    .withSymbol(model.getSymbol())
                    .build();
}
