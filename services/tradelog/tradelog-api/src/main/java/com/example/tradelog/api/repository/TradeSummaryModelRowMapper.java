package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TradeSummaryModelRowMapper implements RowMapper<TradeSummaryModel> {

    @Override
    public TradeSummaryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        Action action = Action.lookup(rs.getString("action_fk"));
        double price = rs.getDouble("price");
        if (Action.BUY.equals(action)) {
            price = price * -1;
        }

        double fees = rs.getDouble("broker_fees");
        double quantity = rs.getInt("quantity");

        return TradeSummaryModel.builder()
                .withSymbol(rs.getString("symbol"))
                .withTotal(price * quantity - fees)
                .withTrades(1)
                .build();
    }

}
