package com.example.tradelog.api.db.converter

import com.example.tradelog.api.core.model.ActionType
import com.example.tradelog.api.core.model.ShareJournalModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class ShareJournalModelRowMapper: RowMapper<ShareJournalModel> {

    override fun mapRow(rs: ResultSet, rowNum: Int): ShareJournalModel {
        val transactionModel = TransactionModelRowMapper(rs).invoke()
        val price = rs.getDouble("price")
        var actualPrice = rs.getDouble("current_price")
        if (actualPrice == 0.00) {
            actualPrice = price
        }

        return ShareJournalModel(transactionDetails = transactionModel,
                price = price, actualPrice = actualPrice,
                quantity = rs.getInt("quantity"),
                action = ActionType.lookup(rs.getString("action_fk")))
    }
}
