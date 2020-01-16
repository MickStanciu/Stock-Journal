package com.example.gateway.api.core.model

import java.time.OffsetDateTime

class DividendJournalModel(val transactionId: String,
                           val accountId: String,
                           val symbol: String,
                           val date: OffsetDateTime,
                           val dividend: Double,
                           val quantity: Int,
                           val groupSelected: Boolean,
                           val legClosed: Boolean)