package com.example.gateway.api.amqp

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AmqpSender(
        private val rabbitTemplate: AmqpTemplate,
        @Value("\${spring.rabbitmq.template.exchange}")
        private val exchange: String,
        @Value("\${spring.rabbitmq.template.routing-key}")
        private val routingKey: String
        ) {


}