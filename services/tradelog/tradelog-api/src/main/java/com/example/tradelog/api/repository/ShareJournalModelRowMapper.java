package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareJournalModelRowMapper implements RowMapper<ShareJournalModel> {

    @Override
    public ShareJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ShareJournalModel.builder()
                .withTransactionId(rs.getString("id"))
                .withAccountId(rs.getString("account_fk"))
                .withDate(TimeConversion.fromTimestamp(rs.getTimestamp("date")))
                .withSymbol(rs.getString("symbol"))
                .withPrice(rs.getDouble("price"))
                .withQuantity(rs.getInt("quantity"))
                .withAction(Action.lookup(rs.getString("action_fk")))
                .withActionType(ActionType.lookup(rs.getString("action_type_fk")))
                .withBrokerFees(rs.getDouble("broker_fees"))
                .build();
    }
}
