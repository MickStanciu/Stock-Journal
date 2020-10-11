package com.example.gateway.api.rest.gateway

import arrow.core.Either
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.gateway.api.core.model.SharePriceModel
import com.example.gateway.api.rest.converter.LastUpdatePriceResponseConverter
import com.example.gateway.api.rest.converter.ShareDataConverter
import com.example.stockdata.api.spec.model.SDPriceItem
import com.example.stockdata.api.spec.model.SDPriceResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class StockDataGateway(
        private val restTemplate: RestTemplate,
        @Value("\${gateway.stockdata.url}") private val url: String) {

    companion object {
        private const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    fun getPrice(symbol: String): Either<ApiException, SharePriceModel> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/price/last-close/{symbol}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)

        var responseDto: SDPriceItem? = null
        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build(symbol).toString(), HttpMethod.GET, HttpEntity<Any>(headers), SDPriceItem::class.java)
            responseDto = responseEntity.body
            if (responseDto != null) {
                Either.Right(ShareDataConverter.toModel(responseDto))
            } else {
                Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR))
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to StockDataApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    fun getSymbolsForUpdate(): Either<ApiException, List<SharePriceModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/symbols/old")
                .queryParam("limit", 3)

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)

        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), SDPriceResponse::class.java)

            val responseDto: SDPriceResponse? = responseEntity.body

            return if (responseDto != null) {
                Either.Right(LastUpdatePriceResponseConverter.toModel(responseDto))
            } else {
                Either.Right(Collections.emptyList())
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to StockDataApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }

    }

    fun updateSymbols(symbols: List<String>): Either<ApiException, Unit> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/symbols")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)

        val requestDto = LastUpdatePriceResponseConverter.toDto(symbols)

        return try {
            restTemplate.exchange(builder.build("").toString(), HttpMethod.POST, HttpEntity<Any>(requestDto, headers), Any::class.java)
            Either.Right(Unit)
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to StockDataApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }
}
