package com.example.gateway.api.core.model

import java.time.OffsetDateTime
import java.util.*

class DividendJournalModel(val transactionId: UUID?,
                           val accountId: UUID,
                           val portfolioId: UUID,
                           val symbol: String,
                           val date: OffsetDateTime,
                           val dividend: Double,
                           val quantity: Int,
                           val groupSelected: Boolean,
                           val legClosed: Boolean)