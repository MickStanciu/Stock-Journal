package com.example.stockdata.api.impl.repository;

import com.example.stockdata.api.spec.model.PriceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HistoryRepository {
    private static final Logger log = LoggerFactory.getLogger(HistoryRepository.class);

    private static final String GET_PRICES_QUERY = "SELECT date, symbol_fk, day_adj_close, per_daily_return FROM price WHERE processed IS FALSE AND symbol_fk = ? ORDER BY date DESC LIMIT ?";
    private static final String UPDATE_PRICES_QUERY = "UPDATE price SET per_daily_return = ?, processed = TRUE WHERE date = ? AND symbol_fk = ? AND day_adj_close = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PriceModel> getPricesForSymbol(String symbol, int batchSize) {
        Object[] parameters = new Object[] { symbol, batchSize };
        return jdbcTemplate.query(GET_PRICES_QUERY, parameters, new PriceEntityMapper());
    }

    public void updatePrices(List<PriceModel> priceModelList) {
        jdbcTemplate.batchUpdate(UPDATE_PRICES_QUERY, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PriceModel model = priceModelList.get(i);
                ps.setDouble(1, model.getPeriodicDailyReturn());
                ps.setDate(2, Date.valueOf(model.getDate()));
                ps.setString(3, model.getSymbol());
                ps.setDouble(4, model.getAdjClose());
            }

            @Override
            public int getBatchSize() {
                return priceModelList.size();
            }
        });
    }

}

class PriceEntityMapper implements RowMapper<PriceModel> {
    public PriceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PriceModel entity = new PriceModel();
        entity.setPeriodicDailyReturn(rs.getDouble("per_daily_return"));
        entity.setDate(rs.getDate("date").toLocalDate());
        entity.setSymbol(rs.getString("symbol_fk"));
        entity.setAdjClose(rs.getDouble("day_adj_close"));
        return entity;
    }
}