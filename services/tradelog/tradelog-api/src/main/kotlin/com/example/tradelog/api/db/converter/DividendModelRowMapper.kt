package com.example.tradelog.api.db.converter

import com.example.tradelog.api.core.model.DividendJournalModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class DividendModelRowMapper: RowMapper<DividendJournalModel> {

    override fun mapRow(rs: ResultSet, rowNum: Int): DividendJournalModel? {
        val transactionModel = TransactionModelRowMapper(rs).invoke()

        return DividendJournalModel(transactionDetails = transactionModel,
                dividend = rs.getDouble("dividend"),
                quantity = rs.getInt("quantity")
        )
    }
}