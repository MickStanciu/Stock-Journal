package com.example.stockdata.api.amqp.converter

import com.example.common.converter.TimeConverter
import com.example.stockdata.api.core.model.PriceModel
import com.example.stockdata.api.spec.model.PriceItemUpdateRequest

class PriceConverter {

    companion object {
        fun toPriceModel(request : PriceItemUpdateRequest) : PriceModel {
            return PriceModel(symbol = request.symbol,
                    lastClose = request.lastClose,
                    lastUpdatedOn = TimeConverter.getOffsetDateTimeNow())
        }
    }
}