package com.example.gateway.api.rest.converter

import com.example.tradelog.api.spec.model.ActiveSymbolsResponse
import java.util.stream.Collectors

fun toModel(dto: ActiveSymbolsResponse): List<String> {
    return dto.symbolsList.stream().collect(Collectors.toList())
}