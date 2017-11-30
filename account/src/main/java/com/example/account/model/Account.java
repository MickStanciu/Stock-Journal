package com.example.account.model;

import java.io.Serializable;
import java.math.BigInteger;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private Tenant tenant;
    private BigInteger id;
    private String name;
    private String email;
    private String password;

    public Account(BigInteger id, Tenant tenant, String name, String email, String password) {
        this.id = id;
        this.tenant = tenant;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public BigInteger getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
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
}
