package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.SummaryMatrixModel
import com.example.gateway.api.spec.model.GWSummaryMatrixItem
import com.example.tradelog.api.spec.model.TLSummaryMatrixItem

class SummaryMatrixConverter {

    companion object {
        fun toModel(dto: TLSummaryMatrixItem): SummaryMatrixModel {
            return SummaryMatrixModel(year = dto.year, month = dto.month, total = dto.total)
        }

        fun toGWDto(models: List<SummaryMatrixModel>): HashMap<Int, GWSummaryMatrixItem> {
            val map = HashMap<Int, GWSummaryMatrixItem>()

            models.forEach {
                if (map.containsKey(it.year)) {
                    val oldDto = map[it.year]!!
                    val monthMap = HashMap(oldDto.monthsMap)
                    val yearlyTotal = oldDto.total + it.total
                    monthMap.putIfAbsent(it.month, it.total)

                    val newDto = GWSummaryMatrixItem.newBuilder()
                            .putAllMonths(monthMap)
                            .setTotal(yearlyTotal)
                            .build()
                    //todo: fix month total
                    //todo: fix year total
                    map[it.year] = newDto
                } else {
                    val monthMap = HashMap<Int, Double>()
                    monthMap[it.month] = it.total
                    val yearlyTotal = it.total
                    val dto = GWSummaryMatrixItem.newBuilder()
                            .putAllMonths(monthMap)
                            .setTotal(yearlyTotal)
                            .build()!!
                    map[it.year] = dto
                }
            }
            return map
        }

        private fun toGWItemDto() {

        }

    }
}