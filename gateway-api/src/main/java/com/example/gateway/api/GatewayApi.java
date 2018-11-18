package com.example.gateway.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.example.gateway.api.gateway")
public class GatewayApi {

    private static final Logger log = LoggerFactory.getLogger(GatewayApi.class);

    public static void main(String[] args) {
        SpringApplication.run(GatewayApi.class, args);
        log.info(generateLogo());
    }

    private static String generateLogo() {
        return  "                                                           \n" +
                "   _____       _                                      _____ _____ \n" +
                "  / ____|     | |                               /\\   |  __ \\_   _|\n" +
                " | |  __  __ _| |_ _____      ____ _ _   _     /  \\  | |__) || |  \n" +
                " | | |_ |/ _` | __/ _ \\ \\ /\\ / / _` | | | |   / /\\ \\ |  ___/ | |  \n" +
                " | |__| | (_| | ||  __/\\ V  V / (_| | |_| |  / ____ \\| |    _| |_ \n" +
                "  \\_____|\\__,_|\\__\\___| \\_/\\_/ \\__,_|\\__, | /_/    \\_\\_|   |_____|\n" +
                "                                      __/ |                       \n" +
                "                                     |___/                        \n" +
                "                                                           \n";
    }
}
