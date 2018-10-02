package com.example.stockdata.api.impl.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryRepository {

    private static final Logger log = LoggerFactory.getLogger(HistoryRepository.class);

    private static final String GET_ADJ_CLOSE_VALUES_QUERY = "SELECT day_adj_close FROM price WHERE processed IS FALSE AND symbol = :symbol ORDER BY date ASC";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> getAdjustedCloseValues(String symbol) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("symbol", symbol);

        return jdbcTemplate.query(GET_ADJ_CLOSE_VALUES_QUERY, (rs, rowNum) -> rs.getLong(1), parameters);
    }
}
