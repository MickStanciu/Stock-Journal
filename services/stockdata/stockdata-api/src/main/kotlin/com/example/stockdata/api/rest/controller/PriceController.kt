package com.example.stockdata.api.rest.controller

import com.example.stockdata.api.core.service.PriceService
import com.example.stockdata.api.rest.controller.PriceController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.stockdata.api.rest.converter.PriceConverter
import com.example.stockdata.api.rest.exception.ExceptionCode
import com.example.stockdata.api.rest.exception.PriceException
import com.example.stockdata.api.rest.validator.RequestValidator
import com.example.stockdata.api.spec.model.SDPriceItem
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/price"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class PriceController(private val priceService: PriceService) {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    @RequestMapping(value = ["/last-close/{symbol}"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getPriceForSymbol(
            @PathVariable symbol: String) : SDPriceItem {

        if (!RequestValidator.validateGetPriceForSymbol(symbol)) {
            throw PriceException(ExceptionCode.BAD_REQUEST)
        }

        val sharedPriceModel = priceService.getPrice(symbol) ?: throw PriceException(ExceptionCode.NO_DATA)
        return PriceConverter.toPriceItemResponse(sharedPriceModel)
    }
}
