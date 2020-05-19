package com.example.stockdata.api.amqp.converter

import com.example.common.converter.TimeConverter
import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.spec.model.SDPriceItem
import java.time.OffsetDateTime

class PriceConverter {

    companion object {
        fun toPriceModel(request : SDPriceItem) : PriceModel {
            //hack
            var lastFailedOn: OffsetDateTime? = null
            if ("null" != request.lastFailedOn) {
                lastFailedOn = TimeConverter.toOffsetDateTime(request.lastFailedOn)
            }

            return PriceModel(symbol = request.symbol,
                    lastClose = request.lastClose,
                    lastUpdatedOn = TimeConverter.toOffsetDateTime(request.lastUpdatedOn!!),
                    lastFailedOn = lastFailedOn,
                    active = request.active)
        }
    }
}