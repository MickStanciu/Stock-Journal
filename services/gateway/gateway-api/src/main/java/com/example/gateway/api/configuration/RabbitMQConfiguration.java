//package com.example.gateway.api.configuration;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RabbitMQConfiguration {
//
//    private String queueName;
//
//    private String topicExchangeName;
//
//    private String routingKey;
//
//    public RabbitMQConfiguration(
//            @Value("${spring.rabbitmq.template.default-receive-queue}") String queueName,
//            @Value("${spring.rabbitmq.template.exchange}") String topicExchangeName,
//            @Value("${spring.rabbitmq.template.routing-key}") String routingKey) {
//        this.queueName = queueName;
//        this.topicExchangeName = topicExchangeName;
//        this.routingKey = routingKey;
//    }
//
//    @Bean
//    Queue queue() {
//      return new Queue(queueName, false);
//    }
//
//    @Bean
//    TopicExchange topicExchange() {
//        return new TopicExchange(topicExchangeName);
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
//    }
//}
