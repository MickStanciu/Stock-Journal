package com.example.tradelog.api.core.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class ShareDataModel(
        val symbol: String,
        val lastUpdatedOn: OffsetDateTime,
        val sector: String,
        val price: Double,
        val marketCapitalization: Double,
        val peRatio: Double,
        val peRatioFuture: Double,
        val bookValue: Double,
        val eps: Double,
        val epsFuture: Double,
        val finvizTarget: BigDecimal,
        val calculatedTarget: BigDecimal
)
