package com.example.stockdata.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.stockdata.api.spec.model.ShareDataModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareDataModelRowMapper implements RowMapper<ShareDataModel> {

    @Override
    public ShareDataModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ShareDataModel.Builder()
                .symbol(rs.getString("symbol"))
                .lastUpdatedOn(TimeConversion.fromTimestamp(rs.getTimestamp("last_updated_on")))
                .sector(rs.getString("sector"))
                .price(rs.getDouble("price"))
                .marketCapitalization(rs.getDouble("market_cap_b"))
                .peRatio(rs.getDouble("p_e_ratio"))
                .peRatioFuture(rs.getDouble("future_p_e_ratio"))
                .bookValue(rs.getDouble("book_value"))
                .eps(rs.getDouble("eps"))
                .epsFuture(rs.getDouble("future_eps"))
                .finvizTarget(rs.getBigDecimal("finviz_target"))
                .calculatedTarget(rs.getBigDecimal("p_e_future_target"))
                .build();
    }
}
