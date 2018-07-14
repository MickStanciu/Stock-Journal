package com.example.accountapi.repository;

import com.example.account.model.RoleInfoModel;
import com.example.account.model.RoleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        return conn.getJdbi().withHandle(handle ->
                handle
                        .createQuery(ROLE_READ_QUERY)
                        .bind(0, id)
                        .bind(1, tenantId)
                        .map(new RoleModelRowMapper())
                        .findOnly()
        );
    }
}

class RoleModelRowMapper implements ResultSetExtractor<List<RoleModel>> {

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


        //https://dzone.com/articles/spring-jdbc-rowmapper-vs-resultsetextractor
        return RoleModel.builder(role).withPermissions(permissions).build();
    }


    @Override
    public List<RoleModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, RoleModel> roles = new HashMap<>();

        while (resultSet.next()) {
            Integer role_id = resultSet.getInt("role_id");


            RoleModel role;
            if (roles.containsKey(role_id)) {
                role = roles.get(role_id);
            } else {
                String role_name = resultSet.getString("role_name");
                role = new RoleModel(role_id, role_name);
            }

            String permissionName = resultSet.getString("permission_name");
            if (permissionName != null) {
                role.getPermissions().add(RoleInfoModel.valueOf(permissionName));
            }

            roles.put(role_id, role);
        }
        return new ArrayList<> (roles.values());
    }
}