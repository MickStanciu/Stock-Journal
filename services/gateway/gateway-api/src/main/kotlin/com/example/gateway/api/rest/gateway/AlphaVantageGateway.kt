package com.example.gateway.api.rest.gateway

import arrow.core.Either
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.gateway.api.core.model.SharePriceModel
import com.example.gateway.api.rest.converter.AlphaVantageCsvConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class AlphaVantageGateway(private val restTemplate: RestTemplate,
                          @Value("\${gateway.alphavantage.url}") private val API_URL: String,
                          @Value("\${gateway.alphavantage.key}") private val API_KEY: String) {

    private final val URL_TEMPLATE = "/query"

    fun getQuoteResponse(symbol: String): Either<ApiException, SharePriceModel> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path(URL_TEMPLATE)
                .queryParam("function", "GLOBAL_QUOTE")
                .queryParam("symbol", symbol)
                .queryParam("interval", "5min")
                .queryParam("outputsize", "full")
                .queryParam("apikey", API_KEY)
                .queryParam("datatype", "csv")


        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")

        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), String::class.java)

            if (responseEntity.body != null) {
                AlphaVantageCsvConverter.toModel(responseEntity.body!!)
            } else {
                Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR))
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to AlphaVantageApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }
}