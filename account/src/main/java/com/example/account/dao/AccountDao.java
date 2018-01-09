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
public class AccountDao {
    private static final Logger log = Logger.getLogger(AccountDao.class);

    private static final String ACCOUNT_QRY = "SELECT a.id as account_id, a.name as account_name, a.password, a.email, " +
            "CAST(t.id as VARCHAR(36)) as tenant_id, t.name as tenant_name, ar.id as role_id, ar.name as role_name, " +
            "ari.name as role_description " +
            "FROM accounts a " +
            "INNER JOIN tenants t on t.id = a.tenant_fk " +
            "INNER JOIN account_roles ar ON a.role_fk = ar.id " +
            "INNER JOIN account_role_info ari ON ar.id = ari.role_fk and a.tenant_fk = ari.tenant_fk " +
            "WHERE a.name = :name and a.password = :password and a.tenant_fk = CAST(:tenant_fk AS uuid)";

    @PersistenceContext
    private EntityManager em;

    public Optional<Account> getAccount(String name, String password, String tenantId) {
        Query q = em.createNativeQuery(ACCOUNT_QRY);

        q.setParameter("name", name);
        q.setParameter("password", password);
        q.setParameter("tenant_fk", tenantId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(mapFromObject(results.get(0)));
    }

    private Account mapFromObject(Object[] result) {
        BigInteger account_id = ((BigInteger) result[0]);
        String name = ((String) result[1]);
        String password = ((String) result[2]);
        String email = ((String) result[3]);

        String tenant_id = ((String) result[4]);
        String tenant_name = ((String) result[5]);

        Integer role_id = ((Integer) result[6]);
        String role_name = ((String) result[7]);
        String role_description = ((String) result[8]);

        Tenant tenant = new Tenant(tenant_id, tenant_name);
        Role role = new Role(role_id, role_name, role_description);
        return new Account(account_id, tenant, role, name, email, password);
    }
}
