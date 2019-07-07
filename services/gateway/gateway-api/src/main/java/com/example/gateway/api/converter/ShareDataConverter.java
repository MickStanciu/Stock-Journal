package com.example.gateway.api.converter;

import com.example.gateway.api.model.ShareDataGWModel;
import com.example.tradelog.api.spec.model.ShareDataModel;

import java.util.function.Function;

public class ShareDataConverter {

    public static Function<ShareDataModel, ShareDataGWModel> toShareDataGWModel = model ->
            ShareDataGWModel.builder()
                    .withBookValue(model.getBookValue())
                    .withCalculatedTarget(model.getCalculatedTarget())
                    .withEps(model.getEps())
                    .withEpsFuture(model.getEpsFuture())
                    .withFinvizTarget(model.getFinvizTarget())
                    .withLastUpdatedOn(model.getLastUpdatedOn())
                    .withMarketCapitalization(model.getMarketCapitalization())
                    .withPeRatio(model.getPeRatio())
                    .withPeRatioFuture(model.getPeRatioFuture())
                    .withPrice(model.getPrice())
                    .withSector(model.getSector())
                    .withSymbol(model.getSymbol())
                    .build();
}
