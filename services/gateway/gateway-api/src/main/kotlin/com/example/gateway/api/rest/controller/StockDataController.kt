package com.example.gateway.api.rest.controller

import com.example.gateway.api.core.service.StockDataService
import com.example.gateway.api.rest.controller.StockDataController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.gateway.api.rest.converter.ShareDataConverter
import com.example.gateway.api.rest.exception.ExceptionCode
import com.example.gateway.api.rest.exception.GatewayApiException
import com.example.gateway.api.spec.model.GWShareDataDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/stockdata"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class StockDataController(private val stockDataService: StockDataService) {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    @RequestMapping(value = ["/{symbol}", "/{symbol}/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getDataBySymbol(
            @RequestHeader(value = "auth-key", required = true) token: String,
            @PathVariable(name = "symbol", required = true) symbol: String): GWShareDataDto {

        //todo: validate input

        val shareDataModel = stockDataService.getPrice(symbol)
                ?: throw GatewayApiException(ExceptionCode.SHARE_DATA_NOT_FOUND)

        return ShareDataConverter.toDto(shareDataModel)
    }
}