package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.PortfolioModel
import com.example.tradelog.api.spec.model.TLPortfolioDto

class PortfolioModelConverter {

    companion object {

        fun toDto(model: PortfolioModel): TLPortfolioDto {
            return TLPortfolioDto.newBuilder()
                    .setId(model.id)
                    .setName(model.name)
                    .build()
        }
    }
}