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
        Double preferredPrice = rs.getDouble("preferred_price");
        if (rs.wasNull()) {
            preferredPrice = null;
        }

        TransactionSettingsModel optionsModel = TransactionSettingsModel.builder()
                .withTransactionId(rs.getString("id"))
                .withPreferredPrice(preferredPrice)
                .withGroupSelected(rs.getBoolean("group_selected"))
                .withLegClosed(rs.getBoolean("leg_closed"))
                .build();

        return TransactionModel.builder()
                .withId(rs.getString("id"))
                .withType(TransactionType.lookup(rs.getString("transaction_type_fk")))
                .withSymbol(rs.getString("symbol"))
                .withDate(TimeConversion.fromTimestamp(rs.getTimestamp("date")))
                .withAccountId(rs.getString("account_fk"))
                .withSettings(optionsModel)
                .build();
    }
}
