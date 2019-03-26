package com.example.account.api.repository;

import com.example.account.api.spec.model.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(AccountRepository.class);

    private static final String ACCOUNT_READ_FIRST_QUERY =
            "SELECT CAST(a.id AS VARCHAR(36)) AS account_id, a.name AS account_name, " +
            "a.password, a.email, a.active " +
            "FROM accounts a " +
            "LIMIT 1";

    private static final String ACCOUNT_READ_BY_EMAIL_AND_PASSWORD_QUERY =
            "SELECT CAST(a.id AS VARCHAR(36)) AS account_id, a.name AS account_name, " +
            "a.password, a.email, a.active " +
            "FROM accounts a " +
            "WHERE a.email = ? AND a.password = ?";

    private static final String ACCOUNT_READ_BY_ID_QUERY =
            "SELECT CAST(a.id AS VARCHAR(36)) AS account_id, a.name AS account_name, a.password, " +
            "a.email, a.active " +
            "FROM accounts a " +
            "WHERE a.id = CAST(? AS uuid)";

    private static final String ACCOUNT_CREATE_QUERY = "INSERT INTO accounts (tenant_fk, role_fk, name, email, password, active) " +
            "VALUES (CAST(? AS uuid), ?, ?, ?, ?, false)";

    private static final String ACCOUNT_CHECK_QUERY =
            "SELECT CAST(a.id AS VARCHAR(36)) AS account_id FROM accounts " +
            "WHERE email = ? AND a.id = CAST(? AS uuid)";

    private static final String ACCOUNT_UPDATE_QUERY = "UPDATE accounts SET " +
            "name = ?, password = ?, email = ?, active = ?, role_fk = ? " +
            "WHERE tenant_fk = CAST(? AS uuid) AND id = ?";

    private JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkFirstRecord() {
        List<AccountModel> results = jdbcTemplate.query(ACCOUNT_READ_FIRST_QUERY, new AccountModelRowMapper());
        return results.size() == 1;
    }

    public AccountModel getAccount(String email, String password) {
        Object[] parameters = new Object[] {email, password};

        List<AccountModel> results = jdbcTemplate.query(ACCOUNT_READ_BY_EMAIL_AND_PASSWORD_QUERY, parameters, new AccountModelRowMapper());

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public AccountModel getAccount(String accountId) {
        Object[] parameters = new Object[] {accountId};

        List<AccountModel> results = jdbcTemplate.query(ACCOUNT_READ_BY_ID_QUERY, parameters, new AccountModelRowMapper());

        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }


    public boolean checkAccount(String tenantId, String email) {
        Object[] parameters = new Object[] {email, tenantId};
        return jdbcTemplate.query(ACCOUNT_CHECK_QUERY, parameters, new AccountModelRowMapper()).size() != 0;
    }

//    public void createAccount(String tenantId, String name, String password, String email, int roleId) {
//        Object[] parameters = new Object[] {tenantId, roleId, name, email, password};
//        jdbcTemplate.update(ACCOUNT_CREATE_QUERY, parameters);
//    }

//    public void updateAccount(String tenantId, long accountId, AccountModel newAccount) {
//        Object[] parameters = new Object[] {newAccount.getName(), newAccount.getPassword(), newAccount.getEmail(), newAccount.isActive(),
//                newAccount.getRole().getId(), tenantId, accountId};
//        jdbcTemplate.update(ACCOUNT_UPDATE_QUERY, parameters);
//    }
}

class AccountModelRowMapper implements RowMapper<AccountModel> {

    @Override
    public AccountModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AccountModel.builder()
                .havingPersonalDetails()
                    .withId(rs.getString("account_id"))
                    .withName(rs.getString("account_name"))
                    .withEmail(rs.getString("email"))
                    .withPassword(rs.getString("password"))
                    .withFlagActive(rs.getBoolean("active"))
                .build();
    }
}
