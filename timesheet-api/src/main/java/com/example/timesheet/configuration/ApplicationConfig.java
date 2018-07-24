package com.example.timesheet.configuration;

import com.example.timesheet.rest.HealthResource;
import com.example.timesheet.rest.TimeSheetResource;

import java.util.ArrayList;
import java.util.List;

public class ApplicationConfig {

    public List<String> getProviderClasses() {
        List<String> provider = new ArrayList<>(1);
        provider.add(JacksonConfig.class.getCanonicalName());
        return provider;
    }

    public List<Class> getResourceClasses() {
        List<Class> classes = new ArrayList<>(2);
        classes.add(HealthResource.class);
        classes.add(TimeSheetResource.class);
        return classes;
    }
}
