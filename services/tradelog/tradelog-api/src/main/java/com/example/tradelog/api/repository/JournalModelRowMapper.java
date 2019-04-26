package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class JournalModelRowMapper implements RowMapper<OptionJournalModel> {

    @Override
    public OptionJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OptionJournalModel.builder()
                .withTransactionId(rs.getString("transaction_id"))
                .withPairTransactionId(rs.getString("transaction_fk"))
                .withAccountId(rs.getString("account_fk"))
                .withDate(fromTimestamp(rs.getTimestamp("date")))
                .withStockSymbol(rs.getString("symbol"))
                .withStockPrice(rs.getFloat("stock_price"))
                .withStrikePrice(rs.getFloat("strike_price"))
                .withExpiryDate(fromTimestamp(rs.getTimestamp("expiry_date")))
                .withImpliedVolatility(rs.getFloat("implied_volatility"))
                .withHistoricalImpliedVolatility(rs.getFloat("implied_volatility_hist"))
                .withProfitProbability(rs.getFloat("profit_probability"))
                .withContracts(rs.getInt("contract_number"))
                .withPremium(rs.getFloat("premium"))
                .withAction(Action.lookup(rs.getString("action_fk")))
                .withActionType(ActionType.lookup(rs.getString("action_type_fk")))
                .withBrokerFees(rs.getFloat("broker_fees"))
                .withMark(rs.getString("mark"))
                .build();
    }

    private OffsetDateTime fromTimestamp(Timestamp timestamp) {
        //TODO: not sure about ZoneId
        return OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp.getTime()), ZoneId.systemDefault());
    }
}
