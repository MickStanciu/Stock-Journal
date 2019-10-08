package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareJournalModelRowMapper implements RowMapper<ShareJournalModel> {

    @Override
    public ShareJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionModel transactionModel = new TransactionModelRowMapper(rs).invoke();

        double price = rs.getDouble("price");
        double actualPrice = rs.getDouble("current_price");
        if (actualPrice == 0) {
            actualPrice = price;
        }

        return new ShareJournalModel(transactionModel, price, actualPrice,
                rs.getInt("quantity"), Action.Companion.lookup(rs.getString("action_fk")),
                rs.getDouble("broker_fees"));
    }

}
