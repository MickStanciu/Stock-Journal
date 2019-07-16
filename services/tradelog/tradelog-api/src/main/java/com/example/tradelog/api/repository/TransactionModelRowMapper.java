package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionOptionsModel;
import com.example.tradelog.api.spec.model.TransactionType;

import java.sql.ResultSet;
import java.sql.SQLException;

class TransactionModelRowMapper {
    private ResultSet rs;

    TransactionModelRowMapper(ResultSet rs) {
        this.rs = rs;
    }

    TransactionModel invoke() throws SQLException {
        TransactionOptionsModel optionsModel = new TransactionOptionsModel();
        optionsModel.setGroupSelected(rs.getBoolean("group_selected"));

        return TransactionModel.builder()
                .withId(rs.getString("id"))
                .withType(TransactionType.lookup(rs.getString("transaction_type_fk")))
                .withSymbol(rs.getString("symbol"))
                .withDate(TimeConversion.fromTimestamp(rs.getTimestamp("date")))
                .withAccountId(rs.getString("account_fk"))
                .withTransactionOptionsModel(optionsModel)
                .build();
    }
}
