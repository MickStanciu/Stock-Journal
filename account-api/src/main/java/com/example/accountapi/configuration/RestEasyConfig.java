package com.example.accountapi.configuration;


import com.example.accountapi.rest.AccountResource;
import com.example.accountapi.rest.HealthResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestEasyConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(HealthResource.class);
        classes.add(AccountResource.class);
        return classes;
    }
}
