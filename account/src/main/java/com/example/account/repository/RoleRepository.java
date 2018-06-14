package com.example.account.repository;

import com.example.account.model.RoleInfoModel;
import com.example.account.model.RoleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleRepository {

    private static final Logger log = LoggerFactory.getLogger(RoleRepository.class);

    private static final String ROLE_READ_QUERY = "SELECT ar.id as role_id, ar.name as role_name, ap.name as permission_name " +
            "FROM account_roles ar " +
            "LEFT JOIN account_permissions ap on ar.id = ap.role_fk and ar.tenant_fk = ap.tenant_fk " +
            "WHERE ar.id = ? AND ar.tenant_fk = CAST(? AS uuid)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RoleModel getRole(String tenantId, int id) {
        Object [] map = new Object[]{id, tenantId};
        List<RoleModel> results = jdbcTemplate.query(ROLE_READ_QUERY, map, new RoleModelRowMapper());

        if (results.size() == 0) {
            return null;
        }

        return results.get(0);
    }
}

class RoleModelRowMapper implements RowMapper<RoleModel> {

    @Override
    public RoleModel mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer role_id = resultSet.getInt("role_id");
        String role_name = resultSet.getString("role_name");
        RoleModel role = new RoleModel(role_id, role_name);

        Set<RoleInfoModel> permissions = new HashSet<>();

        //todo: put a breakpoint ?
//        for(Object[] result : results) {
//            String permissionName = (String) result[2];
//            if (permissionName != null) {
//                permissions.add(RoleInfoModel.valueOf(permissionName));
//            }
//        }

        return RoleModel.builder(role).withPermissions(permissions).build();
    }
}