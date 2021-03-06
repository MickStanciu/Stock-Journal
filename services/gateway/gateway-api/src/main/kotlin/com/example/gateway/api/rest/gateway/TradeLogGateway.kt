package com.example.gateway.api.rest.gateway

import arrow.core.Either
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.gateway.api.core.model.*
import com.example.gateway.api.rest.converter.*
import com.example.tradelog.api.spec.model.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

@Service
class TradeLogGateway(
        private val restTemplate: RestTemplate,
        @Value("\${gateway.tradelog.url}") private val url: String
) {

    companion object {
        private const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
        private const val PORTFOLIO_ID_PARAM_NAME = "portfolio-id"
        private const val ACCOUNT_ID_HEADER_NAME = "x-account-id"
    }

    fun getAllActiveSymbols(): Either<ApiException, List<String>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/transactions/active-symbols")
        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)

        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), TLActiveSymbolsResponse::class.java)

            val dto: TLActiveSymbolsResponse? = responseEntity.body

            return if (dto != null) {
                Either.Right(ActiveSymbolsResponseConverter.toModel(dto))
            } else {
                Either.Right(Collections.emptyList())
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    @Async("asyncExecutor")
    fun getAllShareTransactions(supportMetadata: SupportMetadata, symbol: String): CompletableFuture<List<ShareJournalModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/shares/{symbol}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val responseEntity = restTemplate
                .exchange(builder.build(symbol).toString(), HttpMethod.GET, HttpEntity<Any>(headers), TLShareTransactionsResponse::class.java)

        val dto: TLShareTransactionsResponse? = responseEntity.body

        return if (dto != null) {
            CompletableFuture.completedFuture(
                    dto.shareItemsList.stream().map { ShareJournalConverter.toModel(it) }.collect(Collectors.toList())
            )
        } else {
            CompletableFuture.completedFuture(Collections.emptyList())
        }
    }

    @Async("asyncExecutor")
    fun getAllOptionTransactions(supportMetadata: SupportMetadata, symbol: String): CompletableFuture<List<OptionJournalModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/options/{symbol}/")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val responseEntity = restTemplate
                .exchange(builder.build(symbol).toString(), HttpMethod.GET, HttpEntity<Any>(headers), TLOptionTransactionsResponse::class.java)

        val dto: TLOptionTransactionsResponse? = responseEntity.body

        return if (dto != null) {
            CompletableFuture.completedFuture(
                    dto.optionItemsList.stream().map { OptionJournalConverter.toModel(it) }.collect(Collectors.toList())
            )
        } else {
            CompletableFuture.completedFuture(Collections.emptyList())
        }
    }

    @Async("asyncExecutor")
    fun getAllDividendTransactions(supportMetadata: SupportMetadata, symbol: String): CompletableFuture<List<DividendJournalModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/dividends/{symbol}/")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val responseEntity = restTemplate
                .exchange(builder.build(symbol).toString(), HttpMethod.GET, HttpEntity<Any>(headers), TLDividendTransactionsResponse::class.java)

        val dto: TLDividendTransactionsResponse? = responseEntity.body

        return if (dto != null) {
            CompletableFuture.completedFuture(
                    dto.dividendItemsList.stream().map { DividendJournalConverter.toModel(it) }.collect(Collectors.toList())
            )
        } else {
            CompletableFuture.completedFuture(Collections.emptyList())
        }
    }

    fun getSummary(supportMetadata: SupportMetadata): Either<ApiException, List<TradeSummaryModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/transactions/summary")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), TLTradeSummaryResponse::class.java)

            val dto: TLTradeSummaryResponse? = responseEntity.body

            return if (dto != null) {
                Either.Right(dto.itemsList.stream().map { TradeSummaryItemConverter.toModel(it) }.collect(Collectors.toList()))
            } else {
                Either.Right(Collections.emptyList())
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }

    }

    fun getSummaryMatrix(supportMetadata: SupportMetadata, sharesOnly: Boolean): Either<ApiException, List<SummaryMatrixModel>> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/transactions/summary/matrix/")
                .queryParam("shares-only", sharesOnly)

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), TLSummaryMatrixResponse::class.java)

            val dto = responseEntity.body

            return if (dto != null) {
                Either.Right(dto.itemsList.stream().map { SummaryMatrixConverter.toModel(it) }.collect(Collectors.toList()))
            } else {
                Either.Right(Collections.emptyList())
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    fun createShareTransaction(supportMetadata: SupportMetadata, model: ShareJournalModel): Either<ApiException, ShareJournalModel> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/shares")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val requestDto = ShareJournalConverter.toTLDto(model)

        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build("").toString(), HttpMethod.POST, HttpEntity<Any>(requestDto, headers), TLShareJournalDto::class.java)

            val responseDto: TLShareJournalDto? = responseEntity.body

            return if (responseDto != null) {
                Either.Right(ShareJournalConverter.toModel(responseDto))
            } else {
                Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR, "Cannot process the response"))
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }

    }

    fun editShareTransaction(supportMetadata: SupportMetadata, model: ShareJournalModel): Either<ApiException, Unit> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/shares/{id}")

        val headers = HttpHeaders()

        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val requestDto = ShareJournalConverter.toTLDto(model)

        return try {
            restTemplate
                    .exchange(builder.build(model.transactionId).toString(), HttpMethod.PUT, HttpEntity<Any>(requestDto, headers), Any::class.java)
            Either.Right(Unit)
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    fun deleteShareTransaction(supportMetadata: SupportMetadata, transactionId: String): Either<ApiException, Unit> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/shares/{id}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        return try {
            restTemplate.exchange(builder.build(transactionId).toString(), HttpMethod.DELETE, HttpEntity<Any>(headers), Any::class.java)
            Either.Right(Unit)
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    fun createOptionTransaction(supportMetadata: SupportMetadata, model: OptionJournalModel): Either<ApiException, OptionJournalModel> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/options")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val requestDto = OptionJournalConverter.toTLDto(model)

        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build("").toString(), HttpMethod.POST, HttpEntity<Any>(requestDto, headers), TLOptionJournalDto::class.java)

            val responseDto: TLOptionJournalDto? = responseEntity.body

            return if (responseDto != null) {
                Either.Right(OptionJournalConverter.toModel(responseDto))
            } else {
                Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR, "Cannot process the response"))
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }

    }

    fun editOptionTransaction(supportMetadata: SupportMetadata, model: OptionJournalModel): Either<ApiException, Unit> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/options/{id}")

        val headers = HttpHeaders()

        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val requestDto = OptionJournalConverter.toTLDto(model)
        return try {
            restTemplate
                    .exchange(builder.build(model.transactionId).toString(), HttpMethod.PUT, HttpEntity<Any>(requestDto, headers), Any::class.java)
            Either.Right(Unit)
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    fun deleteOptionTransaction(supportMetadata: SupportMetadata, transactionId: String): Either<ApiException, Unit> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/options/{id}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        return try {
            restTemplate.exchange(builder.build(transactionId).toString(), HttpMethod.DELETE, HttpEntity<Any>(headers), Any::class.java)
            Either.Right(Unit)
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    fun updateTransactionSettings(supportMetadata: SupportMetadata, model: TransactionSettingsModel): Either<ApiException, Unit> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("transactions/settings/{id}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val requestDto = TransactionSettingsConverter.toDto(model)

        return try {
            restTemplate.exchange(builder.build(model.transactionId).toString(), HttpMethod.PUT, HttpEntity<Any>(requestDto, headers), Any::class.java)
            Either.Right(Unit)
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    fun createDividendTransaction(supportMetadata: SupportMetadata, model: DividendJournalModel): Either<ApiException, DividendJournalModel> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/dividends")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        val requestDto = DividendJournalConverter.toTLDto(model)

        return try {
            val responseEntity = restTemplate
                    .exchange(builder.build("").toString(), HttpMethod.POST, HttpEntity<Any>(requestDto, headers), TLDividendJournalDto::class.java)

            val responseDto: TLDividendJournalDto? = responseEntity.body

            if (responseDto != null) {
                Either.Right(DividendJournalConverter.toModel(responseDto))
            } else {
                Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR, "Cannot process the response"))
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }

    }

    fun deleteDividendTransaction(supportMetadata: SupportMetadata, transactionId: String): Either<ApiException, Unit> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/dividends/{id}")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, supportMetadata.accountId.toString())
        headers.set(PORTFOLIO_ID_PARAM_NAME, supportMetadata.portfolioId.toString())

        return try {
            restTemplate.exchange(builder.build(transactionId).toString(), HttpMethod.DELETE, HttpEntity<Any>(headers), Any::class.java)
            Either.Right(Unit)
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }

    fun getDefaultPortfolio(accountId: UUID): Either<ApiException, PortfolioModel> {
        val builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/portfolios/default/")

        val headers = HttpHeaders()
        headers.set("Content-Type", PROTOBUF_MEDIA_TYPE_VALUE)
        headers.set(ACCOUNT_ID_HEADER_NAME, accountId.toString())

        return try {
            val responseEntity = restTemplate.exchange(builder.build("").toString(), HttpMethod.GET, HttpEntity<Any>(headers), TLPortfolioDto::class.java)
            val responseDto: TLPortfolioDto? = responseEntity.body

            if (responseDto != null) {
                Either.Right(PortfolioConverter.toModel(responseDto))
            } else {
                Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_DATA_CONVERSION_ERROR, "Cannot process the response"))
            }
        } catch (rce: RestClientException) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_CONNECTION_ERROR, "Could not connect to TradingLogApi"))
        } catch (ex: Exception) {
            Either.Left(ApiException(ApiExceptionCode.EXTERNAL_API_OTHER_ERROR, ex.message))
        }
    }
}
