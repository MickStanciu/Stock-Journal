package com.example.stockdata.api.impl.repository;

import com.example.stockdata.api.spec.model.PriceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HistoryRepository {
    private static final Logger log = LoggerFactory.getLogger(HistoryRepository.class);

    private static final String GET_PRICES_QUERY = "SELECT date, day_adj_close FROM price WHERE processed IS FALSE AND symbol_fk = :symbol_fk ORDER BY date DESC";
    private static final String UPDATE_PRICES_QUERY = "UPDATE price SET per_daily_return = :per_daily_return, processed = TRUE WHERE date = :date AND symbol_fk = :symbol_fk AND day_adj_close = :day_adj_close";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<PriceModel> getPricesForSymbol(String symbol) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("symbol_fk", symbol);
        return jdbcTemplate.query(GET_PRICES_QUERY, parameters, new PriceEntityMapper());
    }

    public void updatePrices(List<PriceModel> priceModelList) {
        List<Map<String, Object>> paramList = new ArrayList<>();

        for (PriceModel model : priceModelList) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("per_daily_return", model.getPeriodicDailyReturn());
            parameters.put("date", model.getDate());
            parameters.put("symbol_fk", model.getSymbol());
            parameters.put("day_adj_close", model.getAdjClose());
            paramList.add(parameters);
        }
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(paramList.toArray());
        jdbcTemplate.batchUpdate(UPDATE_PRICES_QUERY, batch);
    }
}

class PriceEntityMapper implements RowMapper<PriceModel> {
    public PriceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PriceModel entity = new PriceModel();
        entity.setDate(rs.getDate("date").toLocalDate());
        entity.setAdjClose(rs.getInt("day_adj_close"));
        return entity;
    }
}