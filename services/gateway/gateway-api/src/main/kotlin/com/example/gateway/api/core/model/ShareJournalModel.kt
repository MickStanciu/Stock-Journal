package com.example.gateway.api.core.model

import java.time.OffsetDateTime

class ShareJournalModel(val transactionId: String,
                        val accountId: String,
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
