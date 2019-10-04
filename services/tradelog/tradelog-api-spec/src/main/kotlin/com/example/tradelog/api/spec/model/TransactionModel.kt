package com.example.tradelog.api.spec.model

import com.fasterxml.jackson.annotation.JsonCreator
import java.time.OffsetDateTime

class TransactionModel @JsonCreator constructor(
        val id: String,
        val accountId: String,
        val date: OffsetDateTime,
        val symbol: String,
        val type: TransactionType,
        val settings: TransactionSettingsModel)
