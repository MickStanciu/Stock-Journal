package com.example.gateway.api.core.model

import java.time.OffsetDateTime

data class SharePriceModel(
        val symbol: String,
        val lastClose: Double,
        var lastUpdatedOn: OffsetDateTime,
        var lastFailedOn: OffsetDateTime?,
        var active: Boolean
)