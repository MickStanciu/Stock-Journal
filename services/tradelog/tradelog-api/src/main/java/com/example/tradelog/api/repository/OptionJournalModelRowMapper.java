package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OptionJournalModelRowMapper implements RowMapper<OptionJournalModel> {

    @Override
    public OptionJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionModel transactionModel = new TransactionModelRowMapper(rs).invoke();

        return OptionJournalModel.builder()
                .withTransactionModel(transactionModel)
                .withStockPrice(rs.getDouble("stock_price"))
                .withStrikePrice(rs.getDouble("strike_price"))
                .withExpiryDate(TimeConversion.fromTimestamp(rs.getTimestamp("expiry_date")))
                .withContracts(rs.getInt("contract_number"))
                .withPremium(rs.getDouble("premium"))
                .withAction(Action.lookup(rs.getString("action_fk")))
                .withActionType(ActionType.lookup(rs.getString("action_type_fk")))
                .withBrokerFees(rs.getDouble("broker_fees"))
                .build();
    }
}
