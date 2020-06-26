package com.example.tradelog.api.core.model

import java.math.BigDecimal

data class TradeSummaryModel(
        val symbol: String,
        val trades: Int,
        val total: BigDecimal,
        val legClosed: Boolean
)
