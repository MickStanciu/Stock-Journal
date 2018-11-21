package com.example.timesheet.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class TimeSheetApi {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetApi.class);

    public static void main(String[] args) {
        SpringApplication.run(TimeSheetApi.class, args);
        log.info(generateLogo());
    }

    private static String generateLogo() {
        return  "                                                           \n" +
                "  _______ _                 _____ _               _            _____ _____ \n" +
                " |__   __(_)               / ____| |             | |     /\\   |  __ \\_   _|\n" +
                "    | |   _ _ __ ___   ___| (___ | |__   ___  ___| |_   /  \\  | |__) || |  \n" +
                "    | |  | | '_ ` _ \\ / _ \\\\___ \\| '_ \\ / _ \\/ _ \\ __| / /\\ \\ |  ___/ | |  \n" +
                "    | |  | | | | | | |  __/____) | | | |  __/  __/ |_ / ____ \\| |    _| |_ \n" +
                "    |_|  |_|_| |_| |_|\\___|_____/|_| |_|\\___|\\___|\\__/_/    \\_\\_|   |_____|\n" +
                "                                                                           \n" +
                "                                                                           ";
    }
}
