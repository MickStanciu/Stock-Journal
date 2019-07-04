package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
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

        BigDecimal price = BigDecimal.valueOf(rs.getDouble("price"));

        if (Action.BUY.equals(action)) {
            price = price.negate();
        }

        BigDecimal fees = BigDecimal.valueOf(rs.getDouble("broker_fees"));
        BigDecimal quantity = BigDecimal.valueOf(rs.getInt("quantity"));

        return TradeSummaryModel.builder()
                .withSymbol(rs.getString("symbol"))
                .withTotal(price.multiply(quantity).min(fees))
                .withTrades(1)
                .build();
    }

    private TradeSummaryModel fromOption(ResultSet rs) throws SQLException {
        BigDecimal premium = BigDecimal.valueOf(rs.getDouble("premium"));
        BigDecimal fees = BigDecimal.valueOf(rs.getDouble("broker_fees"));
        BigDecimal quantity = BigDecimal.valueOf(rs.getInt("contract_number"));

        return TradeSummaryModel.builder()
                .withSymbol(rs.getString("symbol"))
                .withTotal(premium.multiply(quantity).multiply(new BigDecimal(100)).min(fees))
                .withTrades(1)
                .build();
    }

    private TradeSummaryModel fromDividend(ResultSet rs) throws SQLException {
        BigDecimal dividend = BigDecimal.valueOf(rs.getDouble("dividend"));
        BigDecimal quantity = BigDecimal.valueOf(rs.getInt("quantity"));

        return TradeSummaryModel.builder()
                .withSymbol(rs.getString("symbol"))
                .withTotal(dividend.multiply(quantity))
                .withTrades(1)
                .build();
    }
}
