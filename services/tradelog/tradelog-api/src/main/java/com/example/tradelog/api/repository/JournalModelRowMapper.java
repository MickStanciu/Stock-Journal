package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JournalModelRowMapper implements RowMapper<OptionJournalModel> {

    @Override
    public OptionJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OptionJournalModel.builder()
                .withTransactionId(rs.getString("transaction_id"))
                .withPairTransactionId(rs.getString("transaction_fk"))
                .withAccountId(rs.getString("account_fk"))
                .withDate(TimeConversion.fromTimestamp(rs.getTimestamp("date")))
                .withStockSymbol(rs.getString("symbol"))
                .withStockPrice(rs.getDouble("stock_price"))
                .withStrikePrice(rs.getDouble("strike_price"))
                .withExpiryDate(TimeConversion.fromTimestamp(rs.getTimestamp("expiry_date")))
                .withImpliedVolatility(rs.getDouble("implied_volatility"))
                .withHistoricalImpliedVolatility(rs.getDouble("implied_volatility_hist"))
                .withProfitProbability(rs.getDouble("profit_probability"))
                .withContracts(rs.getInt("contract_number"))
                .withPremium(rs.getDouble("premium"))
                .withAction(Action.lookup(rs.getString("action_fk")))
                .withActionType(ActionType.lookup(rs.getString("action_type_fk")))
                .withBrokerFees(rs.getDouble("broker_fees"))
                .withMark(rs.getString("mark"))
                .build();
    }
}
