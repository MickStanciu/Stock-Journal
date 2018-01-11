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

    private static final String ACCOUNT_READ_QUERY = "SELECT a.id as account_id, a.name as account_name, a.password, " +
            "a.email, CAST(a.tenant_fk as VARCHAR(36)) as tenant_id, ar.id as role_id, ar.name as role_name, " +
            "ari.name as role_description " +
            "FROM accounts a " +
            "INNER JOIN account_roles ar ON a.role_fk = ar.id " +
            "INNER JOIN account_role_info ari ON ar.id = ari.role_fk and a.tenant_fk = ari.tenant_fk " +
            "WHERE a.name = :name and a.password = :password and a.tenant_fk = CAST(:tenant_fk AS uuid)";

    private static final String ACCOUNT_CREATE_QUERY = "INSERT INTO accounts (tenant_fk, role_fk, name, email, password) " +
            "VALUES (CAST(:tenant_fk AS uuid), :role_fk, :name, :email, :password)";

    private static final String ACCOUNT_CHECK_QUERY = "SELECT id FROM accounts " +
            "WHERE name = :name and tenant_fk = CAST(:tenant_fk AS uuid)";

    @PersistenceContext
    private EntityManager em;

    public Optional<Account> getAccount(String name, String password, String tenantId) {
        Query q = em.createNativeQuery(ACCOUNT_READ_QUERY);

        q.setParameter("name", name);
        q.setParameter("password", password);
        q.setParameter("tenant_fk", tenantId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(mapFromObject(results.get(0)));
    }

    public boolean checkAccount(String name, String tenantId) {
        Query q = em.createNativeQuery(ACCOUNT_CHECK_QUERY);
        q.setParameter("name", name);
        q.setParameter("tenant_fk", tenantId);

        List<Object[]> results = q.getResultList();
        return results.size() != 0;
    }


    public void createAccount(String name, String password, String email, String tenantId, int roleId) {
        Query q = em.createNativeQuery(ACCOUNT_CREATE_QUERY);
        q.setParameter("name", name);
        q.setParameter("password", password);
        q.setParameter("email", email);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("role_fk", roleId);
        q.executeUpdate();
    }

    private Account mapFromObject(Object[] result) {
        BigInteger account_id = ((BigInteger) result[0]);
        String name = ((String) result[1]);
        String password = ((String) result[2]);
        String email = ((String) result[3]);

        String tenant_id = ((String) result[4]);

        Integer role_id = ((Integer) result[5]);
        String role_name = ((String) result[6]);
        String role_description = ((String) result[7]);

        Role role = new Role(role_id, role_name, role_description);
        return new Account(tenant_id, account_id, role, name, email, password);
    }
}
