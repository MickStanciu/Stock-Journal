package com.example.tenant.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TenantApi {

    private static final Logger log = LoggerFactory.getLogger(TenantApi.class);

    public static void main(String[] args) {
        SpringApplication.run(TenantApi.class, args);
        log.info(generateLogo());
    }

    private static String generateLogo() {
        return  "                                                           \n" +
                "  _______                     _              _____ _____ \n" +
                " |__   __|                   | |       /\\   |  __ \\_   _|\n" +
                "    | | ___ _ __   __ _ _ __ | |_     /  \\  | |__) || |  \n" +
                "    | |/ _ \\ '_ \\ / _` | '_ \\| __|   / /\\ \\ |  ___/ | |  \n" +
                "    | |  __/ | | | (_| | | | | |_   / ____ \\| |    _| |_ \n" +
                "    |_|\\___|_| |_|\\__,_|_| |_|\\__| /_/    \\_\\_|   |_____|\n" +
                "                                                         \n" +
                "                                                         ";
    }
}
