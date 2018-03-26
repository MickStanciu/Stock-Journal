package com.example.gatewayapi.model;

import java.io.Serializable;
import java.math.BigInteger;

public class AccountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private BigInteger id;

    public AccountDto(BigInteger id, String name, String email) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
