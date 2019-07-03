package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TradeSummaryModelRowMapper implements RowMapper<TradeSummaryModel> {

    @Override
    public TradeSummaryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionType transactionType = TransactionType.lookup(rs.getString("transaction_type_fk"));

        if (TransactionType.SHARE.equals(transactionType)) {
            return fromStock(rs);
        } else if (TransactionType.OPTION.equals(transactionType)) {
            return fromOption(rs);
        } else if (TransactionType.DIVIDEND.equals(transactionType)) {
            return fromDividend(rs);
        }

        throw new IllegalArgumentException("Received unknown transaction type: " + transactionType);
    }

    private TradeSummaryModel fromStock(ResultSet rs) throws SQLException {
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

    private TradeSummaryModel fromOption(ResultSet rs) throws SQLException {
        double premium = rs.getDouble("premium");
        double fees = rs.getDouble("broker_fees");
        double quantity = rs.getInt("contract_number");

        return TradeSummaryModel.builder()
                .withSymbol(rs.getString("symbol"))
                .withTotal(premium * quantity * 100 - fees)
                .withTrades(1)
                .build();
    }

    private TradeSummaryModel fromDividend(ResultSet rs) throws SQLException {
        double dividend = rs.getDouble("dividend");
        int quantity = rs.getInt("quantity");

        return TradeSummaryModel.builder()
                .withSymbol(rs.getString("symbol"))
                .withTotal(dividend * quantity)
                .withTrades(1)
                .build();
    }
}
