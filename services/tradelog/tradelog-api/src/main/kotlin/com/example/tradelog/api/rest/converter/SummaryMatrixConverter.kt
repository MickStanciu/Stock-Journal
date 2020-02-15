package com.example.tradelog.api.rest.converter

import com.example.tradelog.api.core.model.SummaryMatrixModel
import com.example.tradelog.api.spec.model.TLSummaryMatrixItem
import com.example.tradelog.api.spec.model.TLSummaryMatrixResponse
import java.util.stream.Collectors

class SummaryMatrixConverter {

    companion object {
        fun toSummaryMatrixResponse(models: List<SummaryMatrixModel>): TLSummaryMatrixResponse {
            val items = models.stream()
                    .map { toSummaryMatrixItem(it) }
                    .collect(Collectors.toList())

            return TLSummaryMatrixResponse.newBuilder()
                    .addAllItems(items)
                    .build()
        }

        private fun toSummaryMatrixItem(it: SummaryMatrixModel): TLSummaryMatrixItem {
            return TLSummaryMatrixItem.newBuilder()
                    .setYear(it.year)
                    .setMonth(it.month)
                    .setTotal(it.total)
                    .build()
        }
    }
}