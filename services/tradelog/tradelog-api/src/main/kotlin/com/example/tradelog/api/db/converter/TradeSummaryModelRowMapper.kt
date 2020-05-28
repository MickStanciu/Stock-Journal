package com.example.tradelog.api.db.converter

import com.example.tradelog.api.core.model.ActionType
import com.example.tradelog.api.core.model.TradeSummaryModel
import com.example.tradelog.api.core.model.TransactionType
import org.springframework.jdbc.core.RowMapper
import java.math.BigDecimal
import java.sql.ResultSet

class TradeSummaryModelRowMapper: RowMapper<TradeSummaryModel> {

    override fun mapRow(rs: ResultSet, rowNum: Int): TradeSummaryModel {

        return when(val transactionType = TransactionType.lookup(rs.getString("transaction_type_fk"))) {
            TransactionType.SHARE -> mapStock(rs)
            TransactionType.OPTION -> mapOption(rs)
            TransactionType.DIVIDEND -> mapDividend(rs)
            else -> throw IllegalArgumentException("Received unknown transaction type: $transactionType")
        }
    }

    private fun mapStock(rs: ResultSet): TradeSummaryModel {
        val actionType = ActionType.lookup(rs.getString("action_fk"))
        var price = BigDecimal.valueOf(rs.getDouble("price"))
        val fees = BigDecimal.valueOf(rs.getDouble("broker_fees"))
        val quantity = BigDecimal.valueOf(rs.getInt("quantity").toLong())

        if (ActionType.BUY === actionType) {
            price = price.negate()
        }
        return TradeSummaryModel(
                symbol = rs.getString("symbol"),
                trades = 1,
                total = price.multiply(quantity).subtract(fees),
                legClosed = rs.getBoolean("leg_closed")
        )
    }

    private fun mapOption(rs: ResultSet): TradeSummaryModel {
        val actionType = ActionType.lookup(rs.getString("action_fk"))
        var premium = BigDecimal.valueOf(rs.getDouble("premium"))
        val fees = BigDecimal.valueOf(rs.getDouble("broker_fees"))
        val quantity = BigDecimal.valueOf(rs.getInt("contract_number").toLong())

        if (ActionType.BUY === actionType) {
            premium = premium.negate()
        }

        return TradeSummaryModel(
                symbol = rs.getString("symbol"),
                trades = 1,
                total = premium.multiply(quantity).multiply(BigDecimal(100)).subtract(fees),
                legClosed = rs.getBoolean("leg_closed")
        )
    }

    private fun mapDividend(rs: ResultSet): TradeSummaryModel {
        val dividend = BigDecimal.valueOf(rs.getDouble("dividend"))
        val quantity = BigDecimal.valueOf(rs.getInt("quantity").toLong())

        return TradeSummaryModel(
                symbol = rs.getString("symbol"),
                trades = 1,
                total = dividend.multiply(quantity),
                legClosed = true
        )
    }
}
