package com.example.stockdata.api.converter

import com.example.stockdata.api.model.PriceModel
import com.example.stockdata.api.spec.model.PriceItemResponse

class PriceConverter {

    companion object {
        fun toPriceItemResponse(model: PriceModel) : PriceItemResponse {
            return PriceItemResponse.newBuilder()
                    .setLastClose(model.lastClose)
                    .setSymbol(model.symbol)
                    .build()
        }
    }
}