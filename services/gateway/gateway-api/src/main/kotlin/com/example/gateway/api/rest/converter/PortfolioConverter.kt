package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.PortfolioModel
import com.example.tradelog.api.spec.model.TLPortfolioDto

class PortfolioConverter {

    companion object {
        fun toModel(dto: TLPortfolioDto): PortfolioModel {
            return PortfolioModel(
                    id = dto.id,
                    name = dto.name)
        }
    }
}