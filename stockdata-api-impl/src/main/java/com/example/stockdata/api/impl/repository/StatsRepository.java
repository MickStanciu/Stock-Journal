package com.example.stockdata.api.impl.repository;


import com.example.stockdata.api.spec.model.PriceStatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StatsRepository {
    private static final Logger log = LoggerFactory.getLogger(StatsRepository.class);

    private static final String UPDATE_STD_QUERY = "UPDATE price_stats SET standard_deviation = ?, date = ? WHERE symbol_fk = ?";
    private static final String MODEL_QUERY = "SELECT symbol_fk, date, standard_deviation FROM price_stats WHERE symbol_fk = ?";
    private static final String CREATE_QUERY = "INSERT INTO price_stats VALUES(?, ?, ?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public StatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PriceStatModel getPriceStats(String symbol) {
        Object[] parameters = new Object[] {symbol};
        List<PriceStatModel> results = jdbcTemplate.query(MODEL_QUERY, parameters, new PriceStatsMapper());
        if (results.size() == 1) {
            return results.get(0);
        }
        return null;
    }

    public void updateStandardDeviation(PriceStatModel model) {
        Object[] parameters = new Object[] {
                model.getStd(),
                Date.valueOf(model.getDate()),
                model.getSymbol()
        };
        jdbcTemplate.update(UPDATE_STD_QUERY, parameters);
    }

    public void createPriceStats(PriceStatModel model) {
        Object[] parameters = new Object[] {
                model.getSymbol(),
                Date.valueOf(model.getDate()),
                model.getStd()
        };
        jdbcTemplate.update(CREATE_QUERY, parameters);
    }
}

class PriceStatsMapper implements RowMapper<PriceStatModel> {
    public PriceStatModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PriceStatModel entity = new PriceStatModel();
        entity.setDate(rs.getDate("date").toLocalDate());
        entity.setSymbol(rs.getString("symbol_fk"));
        entity.setStd(rs.getDouble("standard_deviation"));
        return entity;
    }
}