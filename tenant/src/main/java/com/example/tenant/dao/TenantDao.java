package com.example.tenant.dao;

import com.example.tenant.model.Tenant;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Stateless
public class TenantDao {
    private static final Logger log = Logger.getLogger(TenantDao.class);

    private static final String TENANT_READ_QUERY = "SELECT CAST(id as VARCHAR(36)) as tenant_id, name as tenant_name " +
            "FROM tenants " +
            "WHERE id = CAST(:tenant_id AS uuid)";

    @PersistenceContext
    private EntityManager em;

    public Optional<Tenant> getTenant(String id) {
        Query q = em.createNativeQuery(TENANT_READ_QUERY);
        q.setParameter("tenant_id", id);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(mapFromObject(results.get(0)));
    }

    private Tenant mapFromObject(Object[] result) {
        String tenant_id = ((String) result[0]);
        String tenant_name = ((String) result[1]);

        return new Tenant(tenant_id, tenant_name);
    }
}
