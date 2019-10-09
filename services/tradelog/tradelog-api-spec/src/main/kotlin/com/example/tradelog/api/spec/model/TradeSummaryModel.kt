package com.example.tradelog.api.spec.model

import java.math.BigDecimal

class TradeSummaryModel(
        val symbol: String,
        val trades: Int,
        val total: BigDecimal
)
