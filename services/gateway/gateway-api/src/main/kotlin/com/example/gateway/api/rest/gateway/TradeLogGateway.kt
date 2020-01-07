package com.example.gateway.api.rest.gateway

import com.example.gateway.api.core.model.DividendJournalModel
import com.example.gateway.api.core.model.OptionJournalModel
import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.rest.converter.ActiveSymbolsResponseConverter
import com.example.gateway.api.rest.converter.DividendTransactionsResponseConverter
import com.example.gateway.api.rest.converter.OptionTransactionsResponseConverter
import com.example.gateway.api.rest.converter.ShareTransactionsResponseConverter
import com.example.tradelog.api.spec.model.ActiveSymbolsResponse
import com.example.tradelog.api.spec.model.DividendTransactionsResponse
import com.example.tradelog.api.spec.model.OptionTransactionsResponse
import com.example.tradelog.api.spec.model.ShareTransactionsResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

@Service
class TradeLogGateway(private val restTemplate: RestTemplate,
                      @Value("\${gateway.tradelog.url}") private val url: String) {

    companion object {
        private val LOG = LoggerFactory.getLogger(TradeLogGateway::class.java)
        private const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
    }

    fun getAllTradedSymbols(accountId: String): List<String> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/transactions/symbols")
        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set("accountId", accountId)

        val responseEntity = restTemplate
                .exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), ActiveSymbolsResponse::class.java)

        val dto: ActiveSymbolsResponse? = responseEntity.body

        return if (dto != null) {
            ActiveSymbolsResponseConverter.toModel(responseEntity.body!!)
        } else {
            Collections.emptyList()
        }
    }

    @Async("asyncExecutor")
    fun getAllShareTransactions(accountId: String, symbol: String): CompletableFuture<List<ShareJournalModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/shares/{symbol}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set("accountId", accountId)

        val responseEntity = restTemplate
                .exchange(builder.build(symbol).toString(), HttpMethod.GET, HttpEntity<Any>(headers), ShareTransactionsResponse::class.java)

        val dto: ShareTransactionsResponse? = responseEntity.body

        return if (dto != null) {
            CompletableFuture.completedFuture(
                    dto.shareItemsList.stream().map { ShareTransactionsResponseConverter.toModel(it) }.collect(Collectors.toList())
            )
        } else {
            CompletableFuture.completedFuture(Collections.emptyList())
        }
    }

    @Async("asyncExecutor")
    fun getAllOptionTransactions(accountId: String, symbol: String): CompletableFuture<List<OptionJournalModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/options/{symbol}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set("accountId", accountId)

        val responseEntity = restTemplate
                .exchange(builder.build(symbol).toString(), HttpMethod.GET, HttpEntity<Any>(headers), OptionTransactionsResponse::class.java)

        val dto: OptionTransactionsResponse? = responseEntity.body

        return if (dto != null) {
            CompletableFuture.completedFuture(
                    dto.optionItemsList.stream().map { OptionTransactionsResponseConverter.toModel(it) }.collect(Collectors.toList())
            )
        } else {
            CompletableFuture.completedFuture(Collections.emptyList())
        }
    }

    @Async("asyncExecutor")
    fun getAllDividendTransactions(accountId: String, symbol: String): CompletableFuture<List<DividendJournalModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/dividends/{symbol}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set("accountId", accountId)

        val responseEntity = restTemplate
                .exchange(builder.build(symbol).toString(), HttpMethod.GET, HttpEntity<Any>(headers), DividendTransactionsResponse::class.java)

        val dto: DividendTransactionsResponse? = responseEntity.body

        return if (dto != null) {
            CompletableFuture.completedFuture(
                    dto.dividendItemsList.stream().map { DividendTransactionsResponseConverter.toModel(it) }.collect(Collectors.toList())
            )
        } else {
            CompletableFuture.completedFuture(Collections.emptyList())
        }
    }
}