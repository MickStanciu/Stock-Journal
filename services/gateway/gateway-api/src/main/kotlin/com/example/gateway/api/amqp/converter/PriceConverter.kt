package com.example.gateway.api.amqp.converter

import com.example.gateway.api.core.model.SharePriceModel
import com.example.stockdata.api.spec.model.SDPriceItemUpdateRequest

class PriceConverter {
    companion object {
        fun toDto(model : SharePriceModel) : SDPriceItemUpdateRequest {
            return SDPriceItemUpdateRequest.newBuilder()
                    .setSymbol(model.symbol)
                    .setLastClose(model.lastClose)
                    .build()
        }
    }
}