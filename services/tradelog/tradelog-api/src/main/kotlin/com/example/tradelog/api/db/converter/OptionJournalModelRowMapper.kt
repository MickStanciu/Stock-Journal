package com.example.tradelog.api.db.converter

import com.example.common.converter.TimeConverter
import com.example.tradelog.api.core.model.ActionType
import com.example.tradelog.api.core.model.OptionJournalModel
import com.example.tradelog.api.core.model.OptionType
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class OptionJournalModelRowMapper: RowMapper<OptionJournalModel> {

    override fun mapRow(rs: ResultSet, rowNum: Int): OptionJournalModel {
        val transactionModel = TransactionModelRowMapper(rs).invoke()

        return OptionJournalModel(
                transactionDetails = transactionModel,
                stockPrice = rs.getDouble("stock_price"),
                strikePrice = rs.getDouble("strike_price"),
                expiryDate = TimeConverter.fromTimestamp(rs.getTimestamp("expiry_date")),
                contracts = rs.getInt("contract_number"),
                premium = rs.getDouble("premium"),
                action = ActionType.lookup(rs.getString("action_fk")),
                optionType = OptionType.lookup(rs.getString("option_type_fk"))
        )
    }

}