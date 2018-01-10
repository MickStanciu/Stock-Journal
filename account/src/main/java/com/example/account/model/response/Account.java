package com.example.account.model.response;

import java.io.Serializable;
import java.math.BigInteger;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private Tenant tenant;
    private Role role;
    private BigInteger id;
    private String name;
    private String email;
    private String password;

    public Account(BigInteger id, Tenant tenant, Role role, String name, String email, String password) {
        this.id = id;
        this.tenant = tenant;
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Account(Builder builder) {
        this.id = builder.id;
        this.tenant = builder.tenant;
        this.role = builder.role;
        this.name = builder.name;
        this.email = builder.name;
        this.password = builder.password;
    }

    public BigInteger getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
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

    public static class Builder {
        private Tenant tenant;
        private Role role;
        private BigInteger id;
        private String name;
        private String email;
        private String password;

        public Builder withTenant(Tenant tenant) {
            this.tenant = tenant;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder withId(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

}
