package com.example.customer.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigInteger id;
    private String firstName;
    private String lastName;
    private String alias;
    private String email;
    private String mobile;
    private Date birthDate;
    private Boolean active;
    private String tenantId;

    public Customer() {
        //required by Jackson
    }

    public Customer(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.alias = builder.alias;
        this.email = builder.email;
        this.mobile = builder.mobile;
        this.birthDate = builder.birthDate;
        this.active = builder.active;
        this.tenantId = builder.tenantId;
    }

    public BigInteger getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAlias() {
        return alias;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Boolean getActive() {
        return active;
    }

    public static class Builder {
        private String tenantId;
        private BigInteger id;
        private String firstName;
        private String lastName;
        private String alias;
        private String email;
        private String mobile;
        private Date birthDate;
        private Boolean active;

        public Builder withId(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder withBirthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder withFlagActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder withTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
