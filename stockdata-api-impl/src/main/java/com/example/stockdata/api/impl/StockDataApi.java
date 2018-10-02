package com.example.stockdata.api.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockDataApi {
    public static void main(String[] args) {
        SpringApplication.run(StockDataApi.class, args);
        System.out.println(generateLogo());
    }

    private static String generateLogo() {
        return  "                                                                           \n" +
                "   _____ _             _      _____        _                   _____ _____ \n" +
                "  / ____| |           | |    |  __ \\      | |            /\\   |  __ \\_   _|\n" +
                " | (___ | |_ ___   ___| | __ | |  | | __ _| |_ __ _     /  \\  | |__) || |  \n" +
                "  \\___ \\| __/ _ \\ / __| |/ / | |  | |/ _` | __/ _` |   / /\\ \\ |  ___/ | |  \n" +
                "  ____) | || (_) | (__|   <  | |__| | (_| | || (_| |  / ____ \\| |    _| |_ \n" +
                " |_____/ \\__\\___/ \\___|_|\\_\\ |_____/ \\__,_|\\__\\__,_| /_/    \\_\\_|   |_____|\n" +
                "                                                                           \n" +
                "                                                                           \n";
    }
}