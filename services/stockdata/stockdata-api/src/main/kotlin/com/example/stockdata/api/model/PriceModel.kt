package com.example.stockdata.api.model

import java.time.OffsetDateTime

data class PriceModel(
        val symbol: String,
        val lastClose: Double,
        var lastUpdatedOn: OffsetDateTime
        )
