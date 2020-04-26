package com.example.stockdata.api.rest.controller

import com.example.stockdata.api.core.service.PriceService
import com.example.stockdata.api.rest.controller.SymbolController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.stockdata.api.rest.converter.PriceConverter
import com.example.stockdata.api.rest.exception.ExceptionCode
import com.example.stockdata.api.rest.exception.PriceException
import com.example.stockdata.api.rest.validator.RequestValidator
import com.example.stockdata.api.spec.model.SDPriceResponse
import com.example.stockdata.api.spec.model.SDUpdateSymbolsRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/symbols"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class SymbolController(private val priceService: PriceService) {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    /*
        Return symbols where prices where not updated for a while
    */
    @RequestMapping(value = ["/old", "/old/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getOldestSymbols(
            @RequestParam(name = "limit") limit: Int): SDPriceResponse {

        if (!RequestValidator.validateGetOldestSymbols(limit)) {
            throw PriceException(ExceptionCode.BAD_REQUEST)
        }

        var adjustedLimit = limit
        if (adjustedLimit > 10) {
            adjustedLimit = 10
        }

        val models = priceService.getNotUpdatedSymbols(adjustedLimit)
        val dtos = models.map { PriceConverter.toPriceItemResponse(it) }

        return SDPriceResponse.newBuilder()
                .addAllPrice(dtos)
                .build()
    }

    /*
        Return all symbols
    */
    @RequestMapping(value = ["/", ""], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    fun updateSymbols(@RequestBody request: SDUpdateSymbolsRequest) {
        priceService.updateSymbols(request.symbolsList)
    }
}