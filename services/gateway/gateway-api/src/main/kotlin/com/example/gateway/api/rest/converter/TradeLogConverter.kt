package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.TradeLogModel
import com.example.gateway.api.spec.model.TradeLogDto
import java.util.stream.Collectors

class TradeLogConverter {

    companion object {
        fun toDto(model: TradeLogModel): TradeLogDto {

            val shareList = model.shareList.stream()
                    .map { ShareTransactionsResponseConverter.toDto(it) }
                    .collect(Collectors.toList())

            return TradeLogDto.newBuilder()
                    .addAllShareList(shareList)
                    .build()
        }
    }
}