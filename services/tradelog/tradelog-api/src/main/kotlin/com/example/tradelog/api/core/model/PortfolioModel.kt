package com.example.tradelog.api.core.model

import java.util.*

data class PortfolioModel(
        val id: UUID,
        val name: String,
        val default: Boolean
)