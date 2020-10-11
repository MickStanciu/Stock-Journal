package com.example.gateway.api.rest.converter

import arrow.core.Either
import com.example.common.converter.TimeConverter
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.gateway.api.core.model.SharePriceModel

class AlphaVantageCsvConverter {

    companion object {
        fun toModel(csv: String): Either<ApiException, SharePriceModel> {
            if ("{}" == csv) {
                return Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR, "Error parsing CSV: $csv"))
            }

            val bits = CsvProcessor.getCsvBits(csv)
            if (bits.isEmpty() || !bits.containsKey("symbol") || !bits.containsKey("previousClose") || !bits.containsKey("latestDay")) {
                return Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR, "Error parsing CSV: $bits"))
            }
            return Either.Right(
                    SharePriceModel(
                        symbol = bits["symbol"]!!,
                        lastClose = bits["previousClose"]!!.toDouble(),
                        lastUpdatedOn = TimeConverter.fromUSDateString(bits["latestDay"]!!),
                        lastFailedOn = null,
                        active = true
                    )
            )
        }
    }
}