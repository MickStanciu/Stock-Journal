package com.example.gatewayapi.configuration;


import com.example.gatewayapi.rest.*;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestEasyConfig extends Application {

//    private final Injector injector;
//
//    public RestEasyConfig() {
//        injector = Guice.createInjector(new GuiceConfig());
//    }


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(HealthResource.class);
        classes.add(ErrorResource.class);
        classes.add(AccountResource.class);
        classes.add(AuthenticationResource.class);
        classes.add(TimesSheetResource.class);
        return classes;
    }

//    @Override
//    public Set<Object> getSingletons() {
//        final Set<Object> objects = new HashSet<>();
////        objects.add(injector.getInstance(ErrorResource.class));
////        objects.add(injector.getInstance(HealthResource.class));
////        objects.add(injector.getInstance(AccountResource.class));
////        objects.add(injector.getInstance(AuthenticationResource.class));
//        return objects;
//    }
}
