package com.example.gateway.api.rest.converter

import com.example.stockdata.api.spec.model.SDLastUpdatePriceResponse
import java.util.stream.Collectors

class LastUpdatePriceResponseConverter {

    companion object {
        fun toModel(dto: SDLastUpdatePriceResponse): List<String> {
            return dto.symbolsList.stream().collect(Collectors.toList())
        }
    }
}
