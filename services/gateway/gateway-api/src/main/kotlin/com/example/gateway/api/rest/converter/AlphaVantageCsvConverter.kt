package com.example.gateway.api.rest.converter

import com.example.common.converter.TimeConverter
import com.example.gateway.api.core.model.SharePriceModel
import org.slf4j.LoggerFactory

class AlphaVantageCsvConverter {

    companion object {
        private val LOG = LoggerFactory.getLogger(AlphaVantageCsvConverter::class.java)

        fun toModel(csv: String): SharePriceModel? {
            if ("{}" == csv) {
                LOG.info("Error parsing CSV: $csv")
                return null
            }

            val bits = CsvProcessor.getCsvBits(csv)
            if (bits.isEmpty() || !bits.containsKey("symbol") || !bits.containsKey("previousClose") || !bits.containsKey("latestDay")) {
                LOG.info("Error parsing CSV: $bits")
                return null
            }
            return SharePriceModel(
                    symbol = bits["symbol"]!!,
                    lastClose = bits["previousClose"]!!.toDouble(),
                    lastUpdatedOn = TimeConverter.fromUSDateString(bits["latestDay"]!!),
                    lastFailedOn = null,
                    active = true
            )
        }
    }
}