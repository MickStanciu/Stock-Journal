package com.example.stockdata.api.converter;

import com.example.stockdata.api.spec.model.ShareDataModel;
import com.example.stockdata.api.spec.model.ShareDataResponse;

import java.util.function.Function;

public class ShareDataConverter {

    public static Function<ShareDataModel, ShareDataResponse> toResponse = model -> ShareDataResponse.newBuilder()
            .setPrice(model.getPrice())
            .setSymbol(model.getSymbol())
            .setLastUpdatedOn(model.getLastUpdatedOn().toString())
            .build();
}
