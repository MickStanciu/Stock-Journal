package com.example.tradelog.api.repository;

import com.example.tradelog.api.spec.model.ShareDataModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
