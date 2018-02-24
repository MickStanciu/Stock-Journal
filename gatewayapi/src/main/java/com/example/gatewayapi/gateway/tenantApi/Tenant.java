package com.example.gatewayapi.gateway.tenantApi;

public class Tenant {

    private String id;
    private String name;

    public Tenant() {
        //required by jackson
    }

    public Tenant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
