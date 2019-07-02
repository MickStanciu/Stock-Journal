package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.TradeSummaryModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TradeSummaryModelRowMapper implements RowMapper<TradeSummaryModel> {

    @Override
    public TradeSummaryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TradeSummaryModel.builder()
                .build();
    }

}
