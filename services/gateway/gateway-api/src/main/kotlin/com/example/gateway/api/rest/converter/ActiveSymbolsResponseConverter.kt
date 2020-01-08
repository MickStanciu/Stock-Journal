package com.example.gateway.api.rest.converter

import java.util.stream.Collectors
import com.example.tradelog.api.spec.model.ActiveSymbolsResponse as TLActiveSymbolsResponse

class ActiveSymbolsResponseConverter {
    companion object {
        fun toModel(dto: TLActiveSymbolsResponse): List<String> {
            return dto.symbolsList.stream().collect(Collectors.toList())
        }
    }
}
