package com.example.account.model;

import java.io.Serializable;
import java.math.BigInteger;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private Role role;
    private BigInteger id;
    private String name;
    private String email;
    private String password;
    private String tenantId;
    private Boolean active;

    public Account() {
        //required by Jackson
    }

    public BigInteger getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Boolean isActive() {
        return active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected Account account;

        public Builder() {
            account = new Account();
        }

        public Builder(Account account) {
            this.account = account;
        }

        public Account build() {
            return account;
        }

        public RoleBuilder havingRole() {
            return new RoleBuilder(account);
        }

        public AccountBuilder havingPersonalDetails() {
            return new AccountBuilder(account);
        }
    }

    public static class RoleBuilder extends Builder {
        RoleBuilder(Account account) {
            this.account = account;
        }

        public RoleBuilder withRole(Role role) {
            account.role = role;
            return this;
        }
    }

    public static class AccountBuilder extends Builder {
        AccountBuilder(Account account) {
            this.account = account;
        }

        public AccountBuilder withId(BigInteger id) {
            account.id = id;
            return this;
        }

        public AccountBuilder withName(String name) {
            account.name = name;
            return this;
        }

        public AccountBuilder withEmail(String email) {
            account.email = email;
            return this;
        }

        public AccountBuilder withPassword(String password) {
            account.password = password;
            return this;
        }

        public AccountBuilder withTenantId(String tenantId) {
            account.tenantId = tenantId;
            return this;
        }

        public AccountBuilder withFlagActive(boolean active) {
            account.active = active;
            return this;
        }
    }

}
