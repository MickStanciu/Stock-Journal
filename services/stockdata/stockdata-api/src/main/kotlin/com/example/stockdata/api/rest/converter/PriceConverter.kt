package com.example.stockdata.api.rest.converter

import com.example.stockdata.api.model.PriceModel
import com.example.stockdata.api.spec.model.PriceItemResponse

class PriceConverter {

    companion object {
        fun toPriceItemResponse(model: PriceModel) : PriceItemResponse {
            return PriceItemResponse.newBuilder()
                    .setLastClose(model.lastClose)
                    .setSymbol(model.symbol)
                    .setLastUpdatedOn(model.lastUpdatedOn.toString())
                    .build()
        }
    }
}