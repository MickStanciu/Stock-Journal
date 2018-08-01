package com.example.gatewayapi.configuration;


import com.example.gatewayapi.rest.AccountResource;
import com.example.gatewayapi.rest.AuthenticationResource;
import com.example.gatewayapi.rest.BurgerController;
import com.example.gatewayapi.rest.ErrorResource;
import com.example.gatewayapi.rest.HealthResource;
import com.example.gatewayapi.rest.TimesSheetResource;
import com.example.gatewayapi.view.HelloView;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestEasyConfig extends Application {


    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        // REST API
        classes.add(HealthResource.class);
        classes.add(ErrorResource.class);
        classes.add(AccountResource.class);
        classes.add(AuthenticationResource.class);
        classes.add(TimesSheetResource.class);

        // VIEW
        classes.add(HelloView.class);
        classes.add(BurgerController.class);

        //?
        classes.add(CorsFilter.class);
        return classes;
    }
}
