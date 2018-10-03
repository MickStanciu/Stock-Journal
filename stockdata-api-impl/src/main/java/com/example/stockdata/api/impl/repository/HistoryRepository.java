package com.example.stockdata.api.impl.repository;

import com.example.stockdata.api.impl.resource.PriceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HistoryRepository {
    private static final Logger log = LoggerFactory.getLogger(HistoryRepository.class);

    private static final String GET_ADJ_CLOSE_VALUES_QUERY = "SELECT date, day_adj_close FROM price WHERE processed IS FALSE AND symbol_fk = :symbol_fk ORDER BY date DESC";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<PriceEntity> getPricesForSymbol(String symbol) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("symbol_fk", symbol);
        return jdbcTemplate.query(GET_ADJ_CLOSE_VALUES_QUERY, parameters, new PriceEntityMapper());
    }
}

class PriceEntityMapper implements RowMapper<PriceEntity> {
    public PriceEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        PriceEntity entity = new PriceEntity();
        entity.setDate(rs.getDate("date").toLocalDate());
        entity.setAdjClose(rs.getInt("day_adj_close"));
        return entity;
    }
}