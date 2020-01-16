package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.TradeSummaryModel
import com.example.tradelog.api.spec.model.TLTradeSummaryItem

class TradeSummaryItemConverter {

    companion object {
        fun toModel(dto: TLTradeSummaryItem): TradeSummaryModel {
            return TradeSummaryModel(symbol = dto.symbol,
                    total = dto.total,
                    trades = dto.trades)
        }
    }
}