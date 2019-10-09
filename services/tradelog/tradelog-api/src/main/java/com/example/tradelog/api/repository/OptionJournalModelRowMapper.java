package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.OptionType;
import com.example.tradelog.api.spec.model.TransactionModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OptionJournalModelRowMapper implements RowMapper<OptionJournalModel> {

    @Override
    public OptionJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionModel transactionModel = new TransactionModelRowMapper(rs).invoke();

        return new OptionJournalModel(transactionModel,
                rs.getDouble("stock_price"),
                rs.getDouble("strike_price"),
                TimeConversion.fromTimestamp(rs.getTimestamp("expiry_date")),
                rs.getInt("contract_number"),
                rs.getDouble("premium"),
                Action.Companion.lookup(rs.getString("action_fk")),
                OptionType.Companion.lookup(rs.getString("option_type_fk")));
    }
}
