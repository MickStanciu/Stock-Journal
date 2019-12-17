package com.example.stockdata.api.mq.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfiguration() {

    @Value("\${spring.rabbitmq.template.default-receive-queue}")
    var queueName: String? = null

    @Value("\${spring.rabbitmq.template.exchange}")
    val topicExchangeName: String? = null

    @Value("\${spring.rabbitmq.template.routing-key}")
    val routingKey: String? = null

    @Bean
    fun queue(): Queue {
        return Queue(queueName, false)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(topicExchangeName)
    }

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey)
    }
}