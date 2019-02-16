package com.example.account.api.spec.model;

import java.io.Serializable;

public class AccountModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private String password;
    private boolean active;

    private RoleModel role;

    private AccountModel() {
        //required by Jackson
    }

    public String getId() {
        return id;
    }

    public RoleModel getRole() {
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

    public boolean isActive() {
        return active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(AccountModel account) {
        return new Builder(account);
    }

    public static class Builder {
        AccountModel account;

        public Builder() {
            account = new AccountModel();
        }

        Builder(AccountModel account) {
            this.account = account;
        }

        public AccountModel build() {
            return account;
        }

        public AccountBuilder havingPersonalDetails() {
            return new AccountBuilder(account);
        }
    }


    public static class AccountBuilder extends Builder {
        AccountBuilder(AccountModel account) {
            this.account = account;
        }

        public AccountBuilder withId(String id) {
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

        public AccountBuilder withFlagActive(boolean active) {
            account.active = active;
            return this;
        }
    }

}
