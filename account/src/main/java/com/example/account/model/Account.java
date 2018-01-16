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

    public Account(String tenantId, BigInteger id, Role role, String name, String email, String password, Boolean active) {
        this.tenantId = tenantId;
        this.id = id;
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public Account(Builder builder) {
        this.tenantId = builder.tenantId;
        this.id = builder.id;
        this.role = builder.role;
        this.name = builder.name;
        this.email = builder.name;
        this.password = builder.password;
        this.active = builder.active;
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

    public static class Builder {
        private Role role;
        private BigInteger id;
        private String name;
        private String email;
        private String password;
        private String tenantId;
        private Boolean active;

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

        public Builder withTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder withFlagActive(boolean active) {
            this.active = active;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

}
