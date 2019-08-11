package com.example.tradelog.api.repository;

import com.example.common.converter.TimeConversion;
import com.example.tradelog.api.spec.model.ShareDataModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ShareDataRepository {

    private static final String STOCK_DATA_READ_BY_SYMBOL =
            "SELECT symbol," +
                    "       last_updated_on, " +
                    "       sector, " +
                    "       price, " +
                    "       market_cap_b, " +
                    "       p_e_ratio, " +
                    "       future_p_e_ratio, " +
                    "       book_value, " +
                    "       eps, " +
                    "       future_eps, " +
                    "       finviz_target, " +
                    "       p_e_future_target " +
                    "FROM shares_data " +
                    "WHERE symbol = ?";

    private static final String SET_FOR_SYMBOL =
            "INSERT INTO shares_data (symbol, last_updated_on, sector, market_cap_b, p_e_ratio, future_p_e_ratio, book_value, eps, future_eps, price, finviz_target, p_e_future_target) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private JdbcTemplate jdbcTemplate;

    public ShareDataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<ShareDataModel> getBySymbol(String symbol) {
        Object[] parameters = new Object[] {symbol};
        List<ShareDataModel> modelList = jdbcTemplate.query(STOCK_DATA_READ_BY_SYMBOL, parameters, new ShareDataModelRowMapper());

        if (modelList.size() == 1) {
            return Optional.ofNullable(modelList.get(0));
        }
        return Optional.empty();
    }

    public boolean setForSymbol(String symbol, ShareDataModel model) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SET_FOR_SYMBOL);
            ps.setString(1, symbol);
            ps.setTimestamp(2, TimeConversion.fromOffsetDateTime(model.getLastUpdatedOn()));
            ps.setString(3, model.getSector());
            ps.setDouble(4, model.getMarketCapitalization());
            ps.setDouble(5, model.getPeRatio());
            ps.setDouble(6, model.getPeRatioFuture());
            ps.setDouble(7, model.getBookValue());
            ps.setDouble(8, model.getEps());
            ps.setDouble(9, model.getEpsFuture());
            ps.setDouble(10, model.getPrice());
            ps.setBigDecimal(11, model.getFinvizTarget());
            ps.setDouble(12, model.getPeRatioFuture());
            return ps;
        }) == 1;
    }
}
