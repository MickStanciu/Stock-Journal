package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DividendModelRowMapper implements RowMapper<DividendJournalModel> {
    @Override
    public DividendJournalModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionModel transactionModel = new TransactionModelRowMapper(rs).invoke();

        return DividendJournalModel.builder()
                .withTransactionModel(transactionModel)
                .withDividend(rs.getDouble("dividend"))
                .withQuantity(rs.getInt("quantity"))
                .build();
    }
}
