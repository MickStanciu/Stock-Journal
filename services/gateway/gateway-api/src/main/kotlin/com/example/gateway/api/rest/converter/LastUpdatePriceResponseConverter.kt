package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.SharePriceModel
import com.example.stockdata.api.spec.model.SDPriceResponse
import com.example.stockdata.api.spec.model.SDUpdateSymbolsRequest

class LastUpdatePriceResponseConverter {

    companion object {
        fun toModel(dto: SDPriceResponse): List<SharePriceModel> {
            return dto.priceList.map { ShareDataConverter.toModel(it) }
        }

        //NOTE: this doesn't belong here
        fun toDto(model: List<String>): SDUpdateSymbolsRequest {
            return SDUpdateSymbolsRequest.newBuilder()
                    .addAllSymbols(model)
                    .build()
        }
    }
}
