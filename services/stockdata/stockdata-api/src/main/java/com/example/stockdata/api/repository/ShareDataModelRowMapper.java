package com.example.stockdata.api.repository;

import com.example.common.converter.TimeConverter;
import com.example.stockdata.api.model.ShareDataModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShareDataModelRowMapper implements RowMapper<ShareDataModel> {

    @Override
    public ShareDataModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShareDataModel shareDataModel = new ShareDataModel();
        shareDataModel.setSymbol(rs.getString("symbol"));
        shareDataModel.setLastUpdatedOn(TimeConverter.fromTimestamp(rs.getTimestamp("last_updated_on")));
        shareDataModel.setSector(rs.getString("sector"));
        shareDataModel.setPrice(rs.getDouble("price"));
        shareDataModel.setMarketCapitalization(rs.getDouble("market_cap_b"));
        shareDataModel.setPeRatio(rs.getDouble("p_e_ratio"));
        shareDataModel.setPeRatioFuture(rs.getDouble("future_p_e_ratio"));
        shareDataModel.setBookValue(rs.getDouble("book_value"));
        shareDataModel.setEps(rs.getDouble("eps"));
        shareDataModel.setEpsFuture(rs.getDouble("future_eps"));
        shareDataModel.setFinvizTarget(rs.getBigDecimal("finviz_target"));
        shareDataModel.setCalculatedTarget(rs.getBigDecimal("p_e_future_target"));
        return shareDataModel;
    }
}
