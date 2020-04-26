package com.example.gateway.api.core.model

import java.time.OffsetDateTime

data class SharePriceModel(
        val symbol: String,
        val lastClose: Double,
        val lastUpdatedOn: OffsetDateTime,
        val lastFailedOn: OffsetDateTime?,
        var active: Boolean
)