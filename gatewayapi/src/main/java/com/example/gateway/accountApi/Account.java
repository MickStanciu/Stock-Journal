package com.example.gateway.accountApi;

import java.math.BigInteger;

public class Account {

    private Role role;
    private BigInteger id;
    private String name;
    private String email;
    private String password;
    private String tenantId;
    private Boolean active;

    public Account() {
        //required by jackson
    }

    public Role getRole() {
        return role;
    }

    public BigInteger getId() {
        return id;
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
}
