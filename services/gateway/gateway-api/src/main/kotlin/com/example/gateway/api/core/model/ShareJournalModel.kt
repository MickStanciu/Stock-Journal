package com.example.gateway.api.core.model

import java.time.OffsetDateTime
import java.util.*

data class ShareJournalModel(val transactionId: UUID?,
                             val accountId: UUID,
                             val portfolioId: UUID,
                             val symbol: String,
                             val date: OffsetDateTime,
                             val price: Double,
                             val preferredPrice: Double,
                             val actualPrice: Double,
                             val quantity: Int,
                             val brokerFees: Double,
                             val groupSelected: Boolean,
                             val legClosed: Boolean,
                             val transactionType: TransactionType,
                             val action: ActionType
)
