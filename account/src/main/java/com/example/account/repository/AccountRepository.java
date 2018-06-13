package com.example.account.repository;

import com.example.account.model.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(AccountRepository.class);

    private static final String ACCOUNT_READ_FIRST_QUERY = "SELECT a.id FROM accounts a " +
            "INNER JOIN account_roles ar ON a.role_fk = ar.id AND CAST(ar.tenant_fk AS uuid) = CAST(a.tenant_fk AS uuid) " +
            "LIMIT 1";

    private static final String ACCOUNT_READ_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT a.id AS account_id, a.name AS account_name, " +
            "a.password, a.email, CAST(a.tenant_fk AS VARCHAR(36)) AS tenant_id, a.active, a.role_fk " +
            "FROM accounts a " +
            "WHERE a.email = ? AND a.password = ? AND a.tenant_fk = CAST(? AS uuid)";



    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkFirstRecord() {
        List list = jdbcTemplate.queryForList(ACCOUNT_READ_FIRST_QUERY);
        return list.size() == 1;
    }

    public AccountModel getAccount(String tenantId, String email, String password) {
        Object [] map = new Object[]{email, password, tenantId};
        List<AccountModel> results = jdbcTemplate.query(ACCOUNT_READ_BY_EMAIL_AND_PASSWORD_QUERY, map, new AccountModelRowMapper());

        if (results.size() == 0) {
            return null;
        }

        return results.get(0);
    }

    public AccountModel getAccount(String tenantId, BigInteger accountId) {
        Query q = em.createNativeQuery(ACCOUNT_READ_BY_ID_QUERY);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("account_id", accountId);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return null;
        }

        return mapFromObject(results.get(0));
    }

    public List<AccountModel> getAccountsByRelationship(String tenantId, BigInteger parentId, int depth) {
        Query q = em.createNativeQuery(ACCOUNTS_READ_BY_RELATIONSHIP);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("parent_fk", parentId);
        q.setParameter("depth", depth);
        q.setParameter("password", "*****");

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Collections.emptyList();
        }

        List<AccountModel> accountList = new ArrayList<>();
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

    public void createAccount(String tenantId, String name, String password, String email, int roleId) {
        Query q = em.createNativeQuery(ACCOUNT_CREATE_QUERY);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("name", name);
        q.setParameter("password", password);
        q.setParameter("email", email);
        q.setParameter("role_fk", roleId);
        q.executeUpdate();
    }

    public void updateAccount(String tenantId, BigInteger accountId, AccountModel newAccount) {
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
}

class AccountModelRowMapper implements RowMapper<AccountModel> {

    @Override
    public AccountModel mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}