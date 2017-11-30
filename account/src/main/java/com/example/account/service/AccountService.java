package com.example.account.service;

import com.example.account.model.Account;
import com.example.account.model.Tenant;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Stateless
public class AccountService {

    private static final Logger log = Logger.getLogger(AccountService.class);

    @PersistenceContext
    private EntityManager em;

    public Account getAccount(String name, String password, String tenantId) {
        Query q = em.createNativeQuery(
                "SELECT a.id as account_id, a.name as account_name, a.password, a.email, CAST(t.id as VARCHAR(36)) as tenant_id, t.name as tenant_name\n" +
                    "FROM accounts a\n" +
                    "INNER JOIN tenants t on t.id = a.tenant_fk\n" +
                    "WHERE a.name = :name and a.password = :password and a.tenant_fk = CAST(:tenant_fk AS uuid)"
        );
        q.setParameter("name", name);
        q.setParameter("password", password);
        q.setParameter("tenant_fk", tenantId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return null;
        }

        return mapFromObject(results.get(0));
    }

    private Account mapFromObject(Object[] result) {
        BigInteger account_id = ((BigInteger) result[0]);
        String name = ((String) result[1]);
        String password = ((String) result[2]);
        String email = ((String) result[3]);
        String tenant_id = ((String) result[4]);
        String tenant_name = ((String) result[5]);

        Tenant tenant = new Tenant(tenant_id, tenant_name);
        return new Account(account_id, tenant, name, email, password);
    }
}
