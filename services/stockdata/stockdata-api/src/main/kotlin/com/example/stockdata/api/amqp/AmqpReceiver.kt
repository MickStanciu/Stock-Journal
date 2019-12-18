package com.example.stockdata.api.amqp

import com.example.stockdata.api.amqp.converter.PriceConverter
import com.example.stockdata.api.core.service.PriceService
import com.example.stockdata.api.spec.model.PriceUpdateRequest
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class AmqpReceiver(val priceService: PriceService) {

    @RabbitListener(queues = ["\${spring.rabbitmq.template.default-receive-queue}"])
    fun receiveMessage(request: PriceUpdateRequest) {
        println("Processing message ... ")
        request.priceList.forEach {
            val priceModel = PriceConverter.toPriceModel(it)
            priceService.updatePrice(priceModel)
        }
    }
}
