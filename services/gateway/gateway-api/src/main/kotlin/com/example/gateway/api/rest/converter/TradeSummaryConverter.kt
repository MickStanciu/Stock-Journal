package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.TradeSummaryModel
import com.example.gateway.api.spec.model.TradeSummaryDto as GWTradeSummaryDto

class TradeSummaryConverter {

    companion object {
        fun toDto(model: TradeSummaryModel): GWTradeSummaryDto {
            return GWTradeSummaryDto.newBuilder()
                    .setSymbol(model.symbol)
                    .setTotal(model.total)
                    .setTrades(model.trades)
                    .build()
        }
    }
}