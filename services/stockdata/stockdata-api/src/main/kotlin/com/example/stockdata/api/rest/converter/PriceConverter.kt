package com.example.stockdata.api.rest.converter

import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.spec.model.SDPriceItemResponse

class PriceConverter {

    companion object {
        fun toPriceItemResponse(model: PriceModel) : SDPriceItemResponse {
            return SDPriceItemResponse.newBuilder()
                    .setLastClose(model.lastClose)
                    .setSymbol(model.symbol)
                    .setLastUpdatedOn(model.lastUpdatedOn.toString())
                    .build()
        }
    }
}
