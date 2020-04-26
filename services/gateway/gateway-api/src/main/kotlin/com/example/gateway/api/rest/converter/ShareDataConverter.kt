package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.SharePriceModel
import com.example.gateway.api.spec.model.GWShareDataDto
import com.example.stockdata.api.spec.model.SDPriceItem
import java.time.OffsetDateTime

class ShareDataConverter {

    companion object {
        fun toDto(model: SharePriceModel): GWShareDataDto {
            return GWShareDataDto.newBuilder()
                    .setLastClose(model.lastClose)
                    .setSymbol(model.symbol)
                    .setLastUpdatedOn(model.lastUpdatedOn.toString())
                    .build()
        }

        fun toModel(dto: SDPriceItem): SharePriceModel {
            var lastFailedOn: OffsetDateTime? = null
            if (dto.lastFailedOn.isNotEmpty()) {
                lastFailedOn = TimeConverter.toOffsetDateTime.apply(dto.lastFailedOn)
            }
            return SharePriceModel(
                    symbol = dto.symbol,
                    lastClose = dto.lastClose,
                    lastUpdatedOn = TimeConverter.toOffsetDateTime.apply(dto.lastUpdatedOn),
                    lastFailedOn = lastFailedOn,
                    active = dto.active
            )
        }

        /* Used only by util controller for debugging */
        fun toModel(dto: GWShareDataDto): SharePriceModel {
            return SharePriceModel(
                    symbol = dto.symbol,
                    lastClose = dto.lastClose,
                    lastUpdatedOn = TimeConverter.toOffsetDateTime.apply(dto.lastUpdatedOn),
                    lastFailedOn = null,
                    active = true
            )
        }
    }
}