package com.example.gateway.api.core.model

import java.time.OffsetDateTime

class SharePriceModel(
        val symbol: String,
        val lastClose: Double,
        val lastUpdatedOn: OffsetDateTime)