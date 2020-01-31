package com.example.gateway.api.rest.converter

import com.example.stockdata.api.spec.model.SDActiveSymbolsResponse
import com.example.stockdata.api.spec.model.SDUpdateSymbolsRequest
import java.util.stream.Collectors

class LastUpdatePriceResponseConverter {

    companion object {
        fun toModel(dto: SDActiveSymbolsResponse): List<String> {
            return dto.symbolsList.stream().collect(Collectors.toList())
        }

        //NOTE: this doesn't belong here
        fun toDto(model: List<String>): SDUpdateSymbolsRequest {
            return SDUpdateSymbolsRequest.newBuilder()
                    .addAllSymbols(model)
                    .build()
        }
    }
}
