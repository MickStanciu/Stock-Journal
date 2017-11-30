package com.example.account.service;

import com.example.account.model.Account;
import com.example.account.model.Tenant;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class AccountService {

    private static final Logger log = Logger.getLogger(AccountService.class);

    @PersistenceContext
    private EntityManager em;

    public Account getAccount(String name, String password, int tenantId) {
        Query q = em.createNativeQuery(
                "SELECT a.id, a.name, a.password, a.email FROM accounts WHERE name = ? and password = ?  and tenant_fk = ?"
        );
        q.setParameter(1, name);
        q.setParameter(2, password);
        q.setParameter(3, tenantId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return null;
        }

        return mapFromObject(results.get(0));
    }

    private Account mapFromObject(Object[] result) {
        String id = ((String) result[0]);
        String name = ((String) result[1]);
        String password = ((String) result[2]);
        String email = ((String) result[3]);

        //temp Tenant
        Tenant tenant = new Tenant(1, "DEMO");
        return new Account(id, tenant, name, email, password);
    }
}
