package com.example.account.api.jms;

import org.springframework.stereotype.Component;


@Component
public class AccountReceiver {

    public void receiveMessage(String jsonMessage) {
//        https://spring.io/guides/gs/messaging-rabbitmq/
        System.out.println("PULA> " + jsonMessage);
    }
}
