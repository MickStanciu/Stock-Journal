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
        double preferredPrice = rs.getDouble("preferred_price");
        if (rs.wasNull()) {
            preferredPrice = 0.0;
        }

        TransactionSettingsModel optionsModel = new TransactionSettingsModel(
                rs.getString("id"),
                preferredPrice,
                rs.getBoolean("group_selected"),
                rs.getBoolean("leg_closed")
        );

        return TransactionModel.builder()
                .withId(rs.getString("id"))
                .withType(TransactionType.Companion.lookup(rs.getString("transaction_type_fk")))
                .withSymbol(rs.getString("symbol"))
                .withDate(TimeConversion.fromTimestamp(rs.getTimestamp("date")))
                .withAccountId(rs.getString("account_fk"))
                .withSettings(optionsModel)
                .build();
    }
}
