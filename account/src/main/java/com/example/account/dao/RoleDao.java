package com.example.account.dao;

import com.example.account.model.response.Account;
import com.example.account.model.response.Role;
import com.example.account.model.response.Tenant;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Stateless
public class RoleDao {

    private static final Logger log = Logger.getLogger(RoleDao.class);

    private static final String ROLE_QRY = "SELECT ar.id as role_id, ar.name as role_name, ari.name as role_description " +
            "FROM account_roles ar " +
            "INNER JOIN account_role_info ari ON ar.id = ari.role_fk " +
            "WHERE ar.id = :role_id AND ari.tenant_fk = CAST(:tenant_fk AS uuid)";

    @PersistenceContext
    private EntityManager em;

    public Optional<Role> getRole(int id, String tenantId) {
        Query q = em.createNativeQuery(ROLE_QRY);

        q.setParameter("role_id", id);
        q.setParameter("tenant_fk", tenantId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(mapFromObject(results.get(0)));
    }

    private Role mapFromObject(Object[] result) {
        Integer role_id = ((Integer) result[0]);
        String role_name = ((String) result[1]);
        String role_description = ((String) result[2]);

        return new Role(role_id, role_name, role_description);
    }
}
