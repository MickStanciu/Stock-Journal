package com.example.tradelog.api.db.converter

import com.example.common.converter.TimeConverter
import com.example.tradelog.api.core.model.TransactionModel
import com.example.tradelog.api.core.model.TransactionSettingsModel
import com.example.tradelog.api.core.model.TransactionType
import java.sql.ResultSet
import java.util.*

class TransactionModelRowMapper(private var rs: ResultSet) {

    fun invoke(): TransactionModel {
        var preferredPrice = rs.getDouble("preferred_price")
        if (rs.wasNull()) {
           preferredPrice = 0.0
        }

        val settingsModel = TransactionSettingsModel(
                transactionId = rs.getObject("id", UUID::class.java),
                preferredPrice = preferredPrice,
                groupSelected = rs.getBoolean("group_selected"),
                legClosed = rs.getBoolean("leg_closed")
                )

        return TransactionModel(
                id = rs.getObject("id", UUID::class.java),
                accountId = rs.getObject("account_fk", UUID::class.java),
                portfolioId = rs.getObject("portfolio_fk", UUID::class.java),
                date = TimeConverter.fromTimestamp(rs.getTimestamp("date")),
                symbol = rs.getString("symbol"),
                type = TransactionType.lookup(rs.getString("transaction_type_fk")),
                brokerFees = rs.getDouble("broker_fees"),
                settings = settingsModel
        )
    }
}
