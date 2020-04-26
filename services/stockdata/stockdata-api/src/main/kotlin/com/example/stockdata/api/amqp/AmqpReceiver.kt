package com.example.stockdata.api.amqp

import com.example.stockdata.api.amqp.converter.PriceConverter
import com.example.stockdata.api.core.service.PriceService
import com.example.stockdata.api.spec.model.SDPriceItem
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class AmqpReceiver(val priceService: PriceService) {

    companion object {
        private val LOG = LoggerFactory.getLogger(AmqpReceiver::class.java)
    }

    @RabbitListener(queues = ["\${spring.rabbitmq.template.default-receive-queue}"])
    fun receiveMessage(request: SDPriceItem) {
        val priceModel = PriceConverter.toPriceModel(request)
        LOG.info("Received message to update price for ${priceModel.symbol}")
        priceService.updatePrice(priceModel)
    }
}
