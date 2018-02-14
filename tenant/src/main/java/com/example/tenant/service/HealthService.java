package com.example.tenant.service;

import com.example.tenant.model.Health;

import javax.ejb.Singleton;
import javax.ejb.Stateful;

@Singleton
public class HealthService {

    private Health health;

    public HealthService() {
        health = new Health();
        health.setRegistryOn(false);
    }

    public boolean isOk() {
        return health.isRegistryOn();
    }

    public void failRegistryCheck() {
        health.setRegistryOn(false);
    }

    public void passRegistryCheck() {
        health.setRegistryOn(true);
    }

    public Health getHealth() {
        return health;
    }

}
