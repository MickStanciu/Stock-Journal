package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.SharePriceModel

class AlphaVantageCsvConverter {

    companion object {
        fun toModel(csv: String): SharePriceModel? {
            val bits = CsvProcessor.getCsvBits(csv)
            if (!bits.containsKey("symbol") || !bits.containsKey("previousClose") || !bits.containsKey("latestDay")) {
                return null
            }
            return SharePriceModel(
                    symbol = bits["symbol"]!!,
                    lastClose = bits["previousClose"]!!.toDouble(),
                    lastUpdatedOn = TimeConverter.fromUSDateString(bits["latestDay"]!!)
            )
        }
    }
}