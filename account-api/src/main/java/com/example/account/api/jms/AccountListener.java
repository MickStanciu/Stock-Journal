package com.example.account.api.jms;

import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class AccountListener {


    @JmsListener(destination = "inbound.queue")
    @SendTo("outbound.queue")
    public String receiveMessage(final Message jsonMessage) throws JMSException {
//        https://www.devglan.com/spring-boot/spring-boot-jms-activemq-example
        System.out.println("PULA");
        return "MESSAGE RECEIVED";
    }
}
