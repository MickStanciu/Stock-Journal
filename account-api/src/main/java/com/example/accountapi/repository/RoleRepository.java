package com.example.accountapi.repository;

import com.example.account.model.RoleInfoModel;
import com.example.account.model.RoleModel;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class RoleRepository {

    private static final Logger log = LoggerFactory.getLogger(RoleRepository.class);

    private static final String ROLE_READ_QUERY = "SELECT ar.id as role_id, ar.name as role_name, ap.name as permission_name " +
            "FROM account_roles ar " +
            "LEFT JOIN account_permissions ap on ar.id = ap.role_fk and ar.tenant_fk = ap.tenant_fk " +
            "WHERE ar.id = ? AND ar.tenant_fk = CAST(? AS uuid)";

    private DatabaseConnection conn;

    @Inject
    public RoleRepository(DatabaseConnection conn) {
        this.conn = conn;
    }

    public RoleModel getRole(String tenantId, int id) {
        List<RoleModel> results = conn.getJdbi().withHandle(handle ->
                handle
                    .createQuery(ROLE_READ_QUERY)
                    .bind(0, id)
                    .bind(1, tenantId)
                    .map(new RoleModelRowMapper())
                    .list()
        );
        return null;
    }
}

class RoleModelRowMapper implements ColumnMapper<RoleModel> {

    private RoleModel role;

    @Override
    public RoleModel map(ResultSet rs, int columnNumber, StatementContext ctx) throws SQLException {
        int role_id = rs.getInt("role_id");
        String role_name = rs.getString("role_name");
        String permissionName = rs.getString("permission_name");
        if (role == null) {
            role = new RoleModel(role_id, role_name);

        }

        role.getPermissions().add(RoleInfoModel.valueOf(permissionName));
        return role;
    }

}