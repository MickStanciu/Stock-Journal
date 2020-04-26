package com.example.stockdata.api.rest.converter

import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.spec.model.SDPriceItem

class PriceConverter {

    companion object {
        fun toPriceItemResponse(model: PriceModel) : SDPriceItem {
            return SDPriceItem.newBuilder()
                    .setLastClose(model.lastClose)
                    .setSymbol(model.symbol)
                    .setLastUpdatedOn(model.lastUpdatedOn.toString())
                    .setLastFailedOn(model.lastFailedOn.toString())
                    .setActive(model.active)
                    .build()
        }
    }
}
