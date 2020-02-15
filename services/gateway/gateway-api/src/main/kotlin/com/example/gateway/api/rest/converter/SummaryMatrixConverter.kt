package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.SummaryMatrixModel
import com.example.gateway.api.spec.model.GWSummaryMatrixItem
import com.example.tradelog.api.spec.model.TLSummaryMatrixItem

class SummaryMatrixConverter {

    companion object {
        fun toModel(dto: TLSummaryMatrixItem): SummaryMatrixModel {
            return SummaryMatrixModel(year = dto.year, month = dto.month, total = dto.total)
        }

        fun toDto(model: SummaryMatrixModel): GWSummaryMatrixItem {
            return GWSummaryMatrixItem.newBuilder()
                    .setYear(model.year)
                    .setMonth(model.month)
                    .setTotal(model.total)
                    .build()
        }

    }
}