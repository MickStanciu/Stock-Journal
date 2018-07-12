package com.example.tenantapi.repository;

import com.example.tenant.model.TenantModel;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Singleton
public class TenantRepository {

    private static final Logger log = LoggerFactory.getLogger(TenantRepository.class);

    private static final String TENANT_READ_QUERY = "SELECT CAST(id as VARCHAR(36)) as tenant_id, name as tenant_name " +
            "FROM tenants " +
            "WHERE id = CAST(? AS uuid)";

    private static final String TENANT_READ_FIRST_QUERY = "SELECT CAST(id as VARCHAR(36)) FROM tenants LIMIT 1;";

    private DatabaseConnection conn;

    @Inject
    public TenantRepository(DatabaseConnection conn) {
        this.conn = conn;
    }

    public TenantModel getTenant(String id) {
        TenantModel result = conn.getJdbi().withHandle(handle ->
            handle
                .createQuery(TENANT_READ_QUERY)
                .bind(0, id)
                .map(new TenantModelRowMapper())
                .findOnly()
        );


//        Object [] map = new Object[]{id};
//        List<TenantModel> results = jdbcTemplate.query(TENANT_READ_QUERY, map, new TenantModelRowMapper());
//
//        if (results.size() == 0) {
//            return null;
//        }
//
//        return results.get(0);
        return null;
    }


    public boolean checkFirstRecord() {
//        List list = jdbcTemplate.queryForList(TENANT_READ_FIRST_QUERY);
//        return list.size() == 1;
        return true;
    }

    private TenantModel map(Map<String, Object> result) {
        return null;
    }
}

class TenantModelRowMapper implements RowMapper<TenantModel> {

    @Override
    public TenantModel map(ResultSet rs, StatementContext ctx) throws SQLException {
        return null;
    }
}

//class TenantModelRowMapper implements RowMapper<TenantModel> {
//
//    @Override
//    public TenantModel mapRow(ResultSet resultSet, int i) throws SQLException {
//        String tenant_id = resultSet.getString("tenant_id");
//        String tenant_name = resultSet.getString("tenant_name");
//
//        return new TenantModel(tenant_id, tenant_name);
//    }
//}
