package com.example.tradelog.api.db.converter

import com.example.tradelog.api.core.model.TransactionModel
import java.sql.ResultSet

class TransactionModelRowMapper(private var rs: ResultSet) {

    fun invoke(): TransactionModel {

    }
}
