package com.example.tenant.dao;

import com.example.tenant.model.TenantModel;
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

    private static final String TENANT_READ_FIRST_QUERY = "SELECT CAST(id as VARCHAR(36)) FROM tenants LIMIT 1;";

    @PersistenceContext
    private EntityManager em;

    public Optional<TenantModel> getTenant(String id) {
        Query q = em.createNativeQuery(TENANT_READ_QUERY);
        q.setParameter("tenant_id", id);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(mapFromObject(results.get(0)));
    }


    public boolean checkFirstRecord() {
        Query q = em.createNativeQuery(TENANT_READ_FIRST_QUERY);
        List<Object[]> results = q.getResultList();
        return results.size() == 1;
    }


    private TenantModel mapFromObject(Object[] result) {
        String tenant_id = ((String) result[0]);
        String tenant_name = ((String) result[1]);

        return new TenantModel(tenant_id, tenant_name);
    }
}
