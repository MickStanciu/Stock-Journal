package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.ShareDataModel
import com.example.gateway.api.spec.model.GWShareDataDto
import com.example.stockdata.api.spec.model.SDPriceItemResponse

class ShareDataConverter {

    companion object {
        fun toDto(model: ShareDataModel): GWShareDataDto {
            return GWShareDataDto.newBuilder()
                    .setLastClose(model.lastClose)
                    .setSymbol(model.symbol)
                    .setLastUpdatedOn(model.lastUpdatedOn.toString())
                    .build()
        }

        fun toModel(dto: SDPriceItemResponse): ShareDataModel {
            return ShareDataModel(
                    symbol = dto.symbol,
                    lastClose = dto.lastClose,
                    lastUpdatedOn = TimeConverter.toOffsetDateTime.apply(dto.lastUpdatedOn))
        }
    }
}