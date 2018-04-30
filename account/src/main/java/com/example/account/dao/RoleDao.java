package com.example.account.dao;

import com.example.account.model.Role;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Stateless
public class RoleDao {

    private static final Logger log = Logger.getLogger(RoleDao.class);

    private static final String ROLE_READ_QUERY = "SELECT ar.id as role_id, ar.name as role_name " +
            "FROM account_roles ar " +
            "WHERE ar.id = :role_id AND ari.tenant_fk = CAST(:tenant_fk AS uuid)";


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

        return Optional.of(mapFromObject(results.get(0)));
    }

    private Role mapFromObject(Object[] result) {
        Integer role_id = ((Integer) result[0]);
        String role_name = ((String) result[1]);

        return new Role(role_id, role_name);
    }
}
