package com.example.stockdata.api.core.model

import java.time.OffsetDateTime

data class PriceModel(
        val symbol: String,
        val lastClose: Double,
        var lastUpdatedOn: OffsetDateTime
        )
