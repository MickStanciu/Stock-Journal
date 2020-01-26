package com.example.gateway.api.core.model

import java.time.OffsetDateTime

class ShareDataModel(
        val symbol: String,
        val lastClose: Double,
        val lastUpdatedOn: OffsetDateTime)