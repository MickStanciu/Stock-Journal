package com.example.gateway.api.service;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private AmqpTemplate rabbitTemplate;
    private String exchange;
    private String routingKey;

    public RabbitMQService(AmqpTemplate rabbitTemplate,
                           @Value("${spring.rabbitmq.template.exchange}") String exchange,
                           @Value("${spring.rabbitmq.template.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
