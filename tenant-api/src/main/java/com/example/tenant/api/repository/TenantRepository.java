package com.example.tenant.api.repository;

import com.example.tenant.api.spec.model.TenantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TenantRepository {

    private static final Logger log = LoggerFactory.getLogger(TenantRepository.class);

    private static final String TENANT_READ_QUERY = "SELECT CAST(id as VARCHAR(36)) as tenant_id, name as tenant_name " +
            "FROM tenants " +
            "WHERE id = CAST(? AS uuid)";

    private static final String TENANT_READ_FIRST_QUERY = "SELECT CAST(id as VARCHAR(36)) FROM tenants LIMIT 1;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TenantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkFirstRecord() {
        List<TenantModel> results = jdbcTemplate.query(TENANT_READ_FIRST_QUERY, new TenantModelRowMapper());
        return results.size() == 1;
    }

    public TenantModel getTenant(String tenantId) {
        Object[] parameters = new Object[] {tenantId};

        List<TenantModel> results = jdbcTemplate.query(TENANT_READ_QUERY, parameters, new TenantModelRowMapper());

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }
}

class TenantModelRowMapper implements RowMapper<TenantModel> {

    @Override
    public TenantModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        String tenant_id = rs.getString("tenant_id");
        String tenant_name = rs.getString("tenant_name");
        return new TenantModel(tenant_id, tenant_name);
    }
}
