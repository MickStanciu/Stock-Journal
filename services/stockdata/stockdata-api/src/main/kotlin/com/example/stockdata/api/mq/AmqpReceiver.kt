package com.example.stockdata.api.mq

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class AmqpReceiver {

    @RabbitListener(queues = ["\${spring.rabbitmq.template.default-receive-queue}"])
    fun receiveMessage(message: String) {
        println("Received $message")
    }
}