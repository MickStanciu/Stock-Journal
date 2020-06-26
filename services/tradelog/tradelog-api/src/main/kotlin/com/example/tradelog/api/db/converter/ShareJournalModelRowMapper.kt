package com.example.tradelog.api.db.converter

import com.example.tradelog.api.core.model.ActionType
import com.example.tradelog.api.core.model.ShareJournalModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class ShareJournalModelRowMapper: RowMapper<ShareJournalModel> {

    override fun mapRow(rs: ResultSet, rowNum: Int): ShareJournalModel {
        val transactionModel = TransactionModelRowMapper(rs).invoke()

        return ShareJournalModel(
                transactionDetails = transactionModel,
                price = rs.getDouble("price"),
                quantity = rs.getInt("quantity"),
                action = ActionType.lookup(rs.getString("action_fk")))
    }
}
