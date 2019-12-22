package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConverter;
import com.example.tradelog.api.core.model.ActionType;
import com.example.tradelog.api.core.model.OptionJournalModel;
import com.example.tradelog.api.core.model.OptionType;
import com.example.tradelog.api.core.model.TransactionModel;
import com.example.tradelog.api.db.converter.TransactionModelRowMapper;
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
                TimeConverter.fromTimestamp(rs.getTimestamp("expiry_date")),
                rs.getInt("contract_number"),
                rs.getDouble("premium"),
                ActionType.Companion.lookup(rs.getString("action_fk")),
                OptionType.Companion.lookup(rs.getString("option_type_fk")));
    }
}
