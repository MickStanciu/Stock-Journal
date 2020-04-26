package com.example.gateway.api.amqp.converter

import com.example.gateway.api.core.model.SharePriceModel
import com.example.stockdata.api.spec.model.SDPriceItem

class PriceConverter {
    companion object {
        fun toDto(model : SharePriceModel) : SDPriceItem {
            return SDPriceItem.newBuilder()
                    .setSymbol(model.symbol)
                    .setLastClose(model.lastClose)
                    .setLastFailedOn(model.lastFailedOn.toString())
                    .setLastUpdatedOn(model.lastUpdatedOn.toString())
                    .setActive(model.active)
                    .build()
        }
    }
}