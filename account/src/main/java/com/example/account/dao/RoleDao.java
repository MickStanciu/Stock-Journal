package com.example.account.dao;

import com.example.account.model.Role;
import com.example.account.model.RoleInfo;
import org.apache.log4j.Logger;
import org.w3c.dom.css.Rect;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Stateless
public class RoleDao {

    private static final Logger log = Logger.getLogger(RoleDao.class);

    private static final String ROLE_READ_QUERY = "SELECT ar.id as role_id, ar.name as role_name, ap.name as permission_name " +
            "FROM account_roles ar " +
            "LEFT JOIN account_permissions ap on ar.id = ap.role_fk and ar.tenant_fk = ap.tenant_fk " +
            "WHERE ar.id = :role_id AND ar.tenant_fk = CAST(:tenant_fk AS uuid)";

    //todo: not done
    private static final String ROLE_UPDATE_QUERY = "";

    @PersistenceContext
    private EntityManager em;

    public Optional<Role> getRole(String tenantId, int id) {
        Query q = em.createNativeQuery(ROLE_READ_QUERY);

        q.setParameter("role_id", id);
        q.setParameter("tenant_fk", tenantId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(mapFromObject(results));
    }

    private Role mapFromObject(List<Object[]> results) {
        Integer role_id = ((Integer) results.get(0)[0]);
        String role_name = ((String) results.get(0)[1]);
        Role role = new Role(role_id, role_name);

        Set<RoleInfo> permissions = new HashSet<>();
        for(Object[] result : results) {

        }

        return role;
    }
}
