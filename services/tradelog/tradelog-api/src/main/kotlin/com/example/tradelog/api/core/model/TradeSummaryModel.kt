package com.example.tradelog.api.core.model

import java.math.BigDecimal

class TradeSummaryModel(
        val symbol: String,
        val trades: Int,
        val total: BigDecimal
)
