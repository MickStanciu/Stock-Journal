package com.example.stockdata.api.mq

import org.springframework.stereotype.Service

@Service
class AmqpReceiver {

    fun receiveMessage(message: String) {
        println("Received $message")
    }
}