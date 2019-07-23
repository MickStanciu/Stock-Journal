package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;

import java.sql.ResultSet;
import java.sql.SQLException;

class TransactionModelRowMapper {
    private ResultSet rs;

    TransactionModelRowMapper(ResultSet rs) {
        this.rs = rs;
    }

    TransactionModel invoke() throws SQLException {
        TransactionSettingsModel optionsModel = new TransactionSettingsModel();
        optionsModel.setGroupSelected(rs.getBoolean("group_selected"));
        optionsModel.setLegClosed(rs.getBoolean("leg_closed"));
        optionsModel.setTransactionId(rs.getString("id"));

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
