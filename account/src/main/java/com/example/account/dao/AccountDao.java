package com.example.account.dao;

import com.example.account.model.Account;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
public class AccountDao {

    private static final Logger log = Logger.getLogger(AccountDao.class);

    private static final String ACCOUNT_READ_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT a.id AS account_id, a.name AS account_name, " +
            "a.password, a.email, CAST(a.tenant_fk AS VARCHAR(36)) AS tenant_id, a.active, a.role_fk " +
            "FROM accounts a " +
            "WHERE a.email = :email AND a.password = :password AND a.tenant_fk = CAST(:tenant_fk AS uuid)";

    private static final String ACCOUNT_READ_BY_ID_QUERY = "SELECT a.id AS account_id, a.name AS account_name, a.password, " +
            "a.email, CAST(a.tenant_fk AS VARCHAR(36)) AS tenant_id, a.active, a.role_fk " +
            "FROM accounts a " +
            "WHERE a.tenant_fk = CAST(:tenant_fk AS uuid) AND a.id = :account_id";

    private static final String ACCOUNT_CREATE_QUERY = "INSERT INTO accounts (tenant_fk, role_fk, name, email, password, active) " +
            "VALUES (CAST(:tenant_fk AS uuid), :role_fk, :name, :email, :password, false)";

    private static final String ACCOUNT_CHECK_QUERY = "SELECT id FROM accounts " +
            "WHERE email = :email AND tenant_fk = CAST(:tenant_fk AS uuid)";

    private static final String ACCOUNT_UPDATE_QUERY = "UPDATE accounts SET " +
            "name = :name, password = :password, email = :email, active = :active, role_fk = :role_fk " +
            "WHERE tenant_fk = CAST(:tenant_fk AS uuid) AND id = :account_id";

    private static final String ACCOUNT_READ_FIRST_QUERY = "SELECT a.id FROM accounts a " +
            "INNER JOIN account_roles ar ON a.role_fk = ar.id AND CAST(ar.tenant_fk AS uuid) = CAST(a.tenant_fk AS uuid) " +
            "LIMIT 1";

    private static final String ACCOUNTS_READ_BY_RELATIONSHIP = "SELECT a.id AS account_id, a.name AS account_name, " +
            ":password as password, a.email, CAST(a.tenant_fk AS VARCHAR(36)) AS tenant_id, a.active, a.role_fk " +
            "FROM account_relationships ar " +
            "INNER JOIN accounts a ON ar.child_fk = a.id AND CAST(ar.tenant_fk AS uuid) = CAST(a.tenant_fk AS uuid) " +
            "WHERE " +
            "ar.tenant_fk = CAST(:tenant_fk AS uuid) " +
            "and ar.parent_fk = :parent_fk " +
            "and ar.depth <= :depth";

    @PersistenceContext
    private EntityManager em;

    public Account getAccount(String tenantId, String email, String password) {
        Query q = em.createNativeQuery(ACCOUNT_READ_BY_EMAIL_AND_PASSWORD_QUERY);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("email", email);
        q.setParameter("password", password);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return null;
        }

        return mapFromObject(results.get(0));
    }


    public Account getAccount(String tenantId, BigInteger accountId) {
        Query q = em.createNativeQuery(ACCOUNT_READ_BY_ID_QUERY);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("account_id", accountId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return null;
        }

        return mapFromObject(results.get(0));
    }

    public List<Account> getAccountsByRelationship(String tenantId, BigInteger parentId, int depth) {
        Query q = em.createNativeQuery(ACCOUNTS_READ_BY_RELATIONSHIP);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("parent_fk", parentId);
        q.setParameter("depth", depth);
        q.setParameter("password", "*****");

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Collections.emptyList();
        }

        List<Account> accountList = new ArrayList<>();
        for(Object[] result : results) {
            accountList.add(mapFromObject(result));
        }
        return accountList;
    }

    public boolean checkAccount(String tenantId, String email) {
        Query q = em.createNativeQuery(ACCOUNT_CHECK_QUERY);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("email", email);

        List<Object[]> results = q.getResultList();
        return results.size() != 0;
    }


    public boolean checkFirstRecord() {
        Query q = em.createNativeQuery(ACCOUNT_READ_FIRST_QUERY);
        List<Object[]> results = q.getResultList();
        return results.size() == 1;
    }


    public void createAccount(String tenantId, String name, String password, String email, int roleId) {
        Query q = em.createNativeQuery(ACCOUNT_CREATE_QUERY);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("name", name);
        q.setParameter("password", password);
        q.setParameter("email", email);
        q.setParameter("role_fk", roleId);
        q.executeUpdate();
    }


    public void updateAccount(String tenantId, BigInteger accountId, Account newAccount) {
        Query q = em.createNativeQuery(ACCOUNT_UPDATE_QUERY);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("account_id", accountId);
        q.setParameter("role_fk", newAccount.getRole().getId());
        q.setParameter("name", newAccount.getName());
        q.setParameter("password", newAccount.getPassword());
        q.setParameter("email", newAccount.getEmail());
        q.setParameter("active", newAccount.isActive());
        q.executeUpdate();
    }


    private Account mapFromObject(Object[] result) {
        return Account.builder()
                .havingPersonalDetails()
                    .withTenantId(((String) result[4]))
                    .withId(((BigInteger) result[0]))
                    .withName(((String) result[1]))
                    .withEmail(((String) result[3]))
                    .withPassword(((String) result[2]))
                    .withFlagActive(((Boolean) result[5]))
                .havingRole()
                    .withRoleId(((Integer) result[6]))
                .build();
    }
}
