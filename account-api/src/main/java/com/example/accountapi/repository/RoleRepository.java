package com.example.accountapi.repository;

import com.example.accountapi.model.RoleInfoModel;
import com.example.accountapi.model.RoleModel;
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
        Object[] parameters = new Object[] {id, tenantId};
        List<RoleModelMapper> results = jdbcTemplate.query(ROLE_READ_QUERY, parameters, new RoleModelRowMapper());

        Set<RoleInfoModel> permissions  = new HashSet<>();
        String roleName = null;

        for (RoleModelMapper roleModelMap : results) {
            roleName = roleModelMap.name;
            permissions.add(RoleInfoModel.valueOf(roleModelMap.permission));
        }

        if (roleName == null || permissions.isEmpty()) {
            return null;
        }

        return RoleModel.builder()
                .withId(id)
                .withName(roleName)
                .withPermissions(permissions)
                .build();
    }
}

class RoleModelMapper {
    private int id;
    String name;
    String permission;

    RoleModelMapper(int id, String name, String permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
    }
}

class RoleModelRowMapper implements RowMapper<RoleModelMapper> {

    @Override
    public RoleModelMapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RoleModelMapper(
                rs.getInt("role_id"),
                rs.getString("role_name"),
                rs.getString("permission_name")
        );
    }
}