package com.example.tenantapi.repository;

import com.example.tenantapi.configuration.Property;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseConnection {

    @Inject
    @Property("datasource.jdbc-url")
    private String JDBC_URL;

    @Inject
    @Property("datasource.username")
    private String dbUser;

    @Inject
    @Property("datasource.password")
    private String dbPass;

    private Jdbi jdbi;

    @PostConstruct
    public void init() {
        jdbi = Jdbi
                .create(JDBC_URL, dbUser, dbPass)
                .installPlugin(new PostgresPlugin());
    }

    Jdbi getJdbi() {
        return jdbi;
    }
}
