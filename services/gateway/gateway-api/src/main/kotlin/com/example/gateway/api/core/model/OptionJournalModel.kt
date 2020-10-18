package com.example.gateway.api.core.model

import java.time.OffsetDateTime
import java.util.*

class OptionJournalModel(val transactionId: UUID?,
                         val accountId: UUID,
                         val portfolioId: UUID,
                         val stockSymbol: String,
                         val stockPrice: Double,
                         val strikePrice: Double,
                         val contracts: Int,
                         val premium: Double,
                         val expiryDate: OffsetDateTime,
                         val date: OffsetDateTime,
                         val brokerFees: Double,
                         val groupSelected: Boolean,
                         val legClosed: Boolean,
                         val optionType: OptionType,
                         val transactionType: TransactionType,
                         val action: ActionType)

