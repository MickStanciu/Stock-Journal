package com.example.account.model;

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
}
