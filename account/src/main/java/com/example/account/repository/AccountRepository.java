package com.example.account.repository;

import com.example.account.model.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    private static final String ACCOUNT_READ_BY_ID_QUERY = "SELECT a.id AS account_id, a.name AS account_name, a.password, " +
            "a.email, CAST(a.tenant_fk AS VARCHAR(36)) AS tenant_id, a.active, a.role_fk " +
            "FROM accounts a " +
            "WHERE a.tenant_fk = CAST(? AS uuid) AND a.id = ?";

    private static final String ACCOUNT_CREATE_QUERY = "INSERT INTO accounts (tenant_fk, role_fk, name, email, password, active) " +
            "VALUES (CAST(? AS uuid), ?, ?, ?, ?, false)";

    private static final String ACCOUNT_CHECK_QUERY = "SELECT id FROM accounts " +
            "WHERE email = ? AND tenant_fk = CAST(? AS uuid)";

    private static final String ACCOUNT_UPDATE_QUERY = "UPDATE accounts SET " +
            "name = ?, password = ?, email = ?, active = ?, role_fk = ? " +
            "WHERE tenant_fk = CAST(? AS uuid) AND id = ?";


    private static final String ACCOUNTS_READ_BY_RELATIONSHIP = "SELECT a.id AS account_id, a.name AS account_name, " +
            ":password as password, a.email, CAST(a.tenant_fk AS VARCHAR(36)) AS tenant_id, a.active, a.role_fk " +
            "FROM account_relationships ar " +
            "INNER JOIN accounts a ON ar.child_fk = a.id AND CAST(ar.tenant_fk AS uuid) = CAST(a.tenant_fk AS uuid) " +
            "WHERE " +
            "ar.tenant_fk = CAST(? AS uuid) " +
            "and ar.parent_fk = ? " +
            "and ar.depth <= ?";

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
        Object [] map = new Object[]{tenantId, accountId};
        List<AccountModel> results = jdbcTemplate.query(ACCOUNT_READ_BY_ID_QUERY, map, new AccountModelRowMapper());

        if (results.size() == 0) {
            return null;
        }

        return results.get(0);
    }

    public List<AccountModel> getAccountsByRelationship(String tenantId, BigInteger parentId, int depth) {
        Object [] map = new Object[]{tenantId, parentId, depth};
        return jdbcTemplate.query(ACCOUNTS_READ_BY_RELATIONSHIP, map, new AccountModelRowMapper());
    }

    public boolean checkAccount(String tenantId, String email) {
        Object [] map = new Object[]{email, tenantId};
        List<AccountModel> results = jdbcTemplate.query(ACCOUNT_CHECK_QUERY, map, new AccountModelRowMapper());
        return results.size() != 0;
    }

    @Transactional
    public void createAccount(String tenantId, String name, String password, String email, int roleId) {
        Object [] map = new Object[]{tenantId, roleId, name, email, password};
        //todo: is it ok?
        jdbcTemplate.update(ACCOUNT_CREATE_QUERY, map);
    }

    @Transactional
    public void updateAccount(String tenantId, BigInteger accountId, AccountModel newAccount) {
        Object [] map = new Object[]{newAccount.getName(), newAccount.getPassword(), newAccount.getEmail(), newAccount.isActive(),
                newAccount.getRole().getId(), tenantId, accountId};
        jdbcTemplate.update(ACCOUNT_UPDATE_QUERY, map);
    }
}

class AccountModelRowMapper implements RowMapper<AccountModel> {

    @Override
    public AccountModel mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}