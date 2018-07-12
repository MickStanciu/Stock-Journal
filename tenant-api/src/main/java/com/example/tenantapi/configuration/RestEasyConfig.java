package com.example.tenantapi.configuration;



import com.example.tenantapi.rest.HealthResource;
import com.example.tenantapi.rest.TenantResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestEasyConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(HealthResource.class);
        classes.add(TenantResource.class);
        return classes;
    }
}
