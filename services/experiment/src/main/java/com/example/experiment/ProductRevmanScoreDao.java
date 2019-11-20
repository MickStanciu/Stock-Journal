package com.example.experiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRevmanScoreDao {

    private static final Logger LOG = LoggerFactory.getLogger(ProductRevmanScoreDao.class);

    private JdbcTemplate jdbcTemplate;

    private static final String CREATE_UPDATE_QUERY =
            "INSERT INTO PRODUCT_REVMAN_SCORES(PRODUCT_CODE, LANGUAGE_CODE, SCORE) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE SCORE = ?";

    private static final String CREATE_UPDATE_QUERY_ALTERNATIVE =
            "INSERT INTO PRODUCT_REVMAN_SCORES(PRODUCT_CODE, LANGUAGE_CODE, SCORE) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE SCORE = VALUES(SCORE)";

    @Autowired
    public ProductRevmanScoreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save / Updates a single model
     * @param model -
     * @return true if was successful.
     */
    public boolean saveUpdate(ProductScoreModel model) {
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, model.getProductCode());
            ps.setString(2, model.getLanguageCode());
            ps.setInt(3, model.getRevmanScore());
            ps.setInt(4, model.getRevmanScore());
            return ps;
        });

        return rows == 1;
    }


    /**
     * Save / Updates a collection of models in a batch-way
     * @param modelList -
     * @return true if ALL records were succesfully created/updated. Otherwise false
     */
    public boolean saveUpdate(List<ProductScoreModel> modelList) {
        if (modelList.isEmpty()) {
            LOG.warn("No insert/updates because no data provided");
            return false;
        }

        int[] rows = jdbcTemplate.batchUpdate(CREATE_UPDATE_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ProductScoreModel model = modelList.get(i);
                ps.setString(1, model.getProductCode());
                ps.setString(2, model.getLanguageCode());
                ps.setInt(3, model.getRevmanScore());
                ps.setInt(4, model.getRevmanScore());
            }

            @Override
            public int getBatchSize() {
                return modelList.size();
            }
        });


        long errorCount = Arrays.stream(rows).filter(f -> f == 0).count();
        if (errorCount > 0) {
            LOG.error("There where {} erroneous insert/updates", errorCount);
        }

        LOG.info("1 There where {} successful insert/updates", Arrays.stream(rows).filter(f -> f == 1).count());
        return errorCount == 0;
    }

    public boolean saveUpdateAlternative(List<ProductScoreModel> modelList) {
        if (modelList.isEmpty()) {
            LOG.warn("No insert/updates because no data provided");
            return false;
        }

        AtomicInteger rows = new AtomicInteger();
        modelList.forEach(model -> {
            rows.addAndGet(jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(CREATE_UPDATE_QUERY);
                ps.setString(1, model.getProductCode());
                ps.setString(2, model.getLanguageCode());
                ps.setInt(3, model.getRevmanScore());
                ps.setInt(4, model.getRevmanScore());
                return ps;
            }));
        });

        LOG.info("2 There where {} successful insert/updates", rows.get());
        return true;
    }

    public boolean saveUpdateAlternative2(List<ProductScoreModel> modelList) {
        if (modelList.isEmpty()) {
            LOG.warn("No insert/updates because no data provided");
            return false;
        }

        String sql = "INSERT INTO PRODUCT_REVMAN_SCORES(PRODUCT_CODE, LANGUAGE_CODE, SCORE) VALUES ";
        StringJoiner values = new StringJoiner(", ");

        modelList.forEach(model -> {
            values.add("(?, ?, ?)");
        });
        sql += values.toString();
        sql += " ON DUPLICATE KEY UPDATE SCORE = VALUES(SCORE)";

        String finalSql = sql;
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(finalSql);
            int argumentIndex = 0;
            for (ProductScoreModel model : modelList) {
                ps.setString(++argumentIndex, model.getProductCode());
                ps.setString(++argumentIndex, model.getLanguageCode());
                ps.setInt(++argumentIndex, model.getRevmanScore());
            }
            return ps;
        });

        LOG.info("3 There where {} successful insert/updates", rows);
        return true;
    }
}




