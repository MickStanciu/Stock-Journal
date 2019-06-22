package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareJournalModelRowMapper implements RowMapper<ShareJournalModel> {

    @Override
    public ShareJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionModel transactionModel = new TransactionModelRowMapper(rs).invoke();

        return ShareJournalModel.builder()
                .withTransactionModel(transactionModel)
                .withPrice(rs.getDouble("price"))
                .withQuantity(rs.getInt("quantity"))
                .withAction(Action.lookup(rs.getString("action_fk")))
                .withActionType(ActionType.lookup(rs.getString("action_type_fk")))
                .withBrokerFees(rs.getDouble("broker_fees"))
                .build();
    }
}
