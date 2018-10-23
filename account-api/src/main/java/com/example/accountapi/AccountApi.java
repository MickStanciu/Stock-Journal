package com.example.accountapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApi.class);

    public static void main(String[] args) {
        SpringApplication.run(AccountApi.class, args);
        log.info(generateLogo());
    }

    private static String generateLogo() {
        return  "                                                           \n" +
                "                                    _            _____ _____ \n" +
                "     /\\                            | |     /\\   |  __ \\_   _|\n" +
                "    /  \\   ___ ___ ___  _   _ _ __ | |_   /  \\  | |__) || |  \n" +
                "   / /\\ \\ / __/ __/ _ \\| | | | '_ \\| __| / /\\ \\ |  ___/ | |  \n" +
                "  / ____ \\ (_| (_| (_) | |_| | | | | |_ / ____ \\| |    _| |_ \n" +
                " /_/    \\_\\___\\___\\___/ \\__,_|_| |_|\\__/_/    \\_\\_|   |_____|\n" +
                "                                                             \n" +
                "                                                             ";
    }
}
