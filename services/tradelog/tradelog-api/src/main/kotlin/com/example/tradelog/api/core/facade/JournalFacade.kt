package com.example.tradelog.api.core.facade

import arrow.core.Either
import arrow.core.flatMap
import com.example.common.service.ServiceError
import com.example.tradelog.api.core.model.*
import com.example.tradelog.api.core.service.DividendJournalService
import com.example.tradelog.api.core.service.OptionJournalService
import com.example.tradelog.api.core.service.ShareJournalService
import com.example.tradelog.api.core.service.TransactionService
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

@Service
class JournalFacade(private val transactionService: TransactionService,
                    private val shareService: ShareJournalService,
                    private val optionService: OptionJournalService,
                    private val dividendService: DividendJournalService
) {
    enum class SummaryMatrixType {
        SHARES,
        OPTIONS_AND_DIVIDENDS
    }

    fun getSummary(accountId: UUID): Either<ServiceError, List<TradeSummaryModel>> {
        //TODO: do parallel call
        //TODO: rewrite it
        val shareSummaries = shareService.getSummaries(accountId).orNull() ?: return Either.Left(ServiceError.DataAccessError("Cannot get share summaries"))
        val optionSummaries = optionService.getSummaries(accountId).orNull() ?: return Either.Left(ServiceError.DataAccessError("Cannot get option summaries"))
        val dividendSummaries = dividendService.getSummaries(accountId).orNull() ?: return Either.Left(ServiceError.DataAccessError("Cannot get dividend summaries"))

        val summaryModelMap: HashMap<String, TradeSummaryModel> = HashMap()

        shareSummaries.forEach { summaryModelMap[it.key] = it.value }

        optionSummaries.forEach {
            if (summaryModelMap.containsKey(it.key)) {
                val storedModel: TradeSummaryModel = summaryModelMap[it.key]!!

                summaryModelMap[it.key] = TradeSummaryModel(symbol = it.key,
                        trades = storedModel.trades + it.value.trades,
                        total = storedModel.total.add(it.value.total),
                        legClosed = storedModel.legClosed)
            } else {
                if (it.value.legClosed) {
                    summaryModelMap[it.key] = it.value
                }
            }
        }

        dividendSummaries.forEach {
            if (summaryModelMap.containsKey(it.key)) {
                val storedModel: TradeSummaryModel = summaryModelMap[it.key]!!

                summaryModelMap[it.key] = TradeSummaryModel(symbol = it.key,
                        trades = storedModel.trades + it.value.trades,
                        total = storedModel.total.add(it.value.total),
                        legClosed = storedModel.legClosed)
            } else {
                if (it.value.legClosed) {
                    summaryModelMap[it.key] = it.value
                }
            }
        }

        return Either.Right(
                summaryModelMap.entries.stream()
                        .sorted(compareBy { it.key })
                        .map { it.value }
                        .collect(Collectors.toList())
        )
    }


    fun getSummaryMatrix(accountId: UUID, portfolioId: UUID, matrixType: SummaryMatrixType): Either<ServiceError, List<SummaryMatrixModel>> {
        val summaryMatrixList = when(matrixType) {
            SummaryMatrixType.OPTIONS_AND_DIVIDENDS -> transactionService.getOptionAndDividendSummaryMatrix(accountId, portfolioId)
            SummaryMatrixType.SHARES -> transactionService.getSharesSummaryMatrix(accountId, portfolioId)
        }


        val groupedMap: (List<SummaryMatrixModel>) -> Map<Pair<Int, Int>, Double> = {
            models -> groupByYearAndMonth(models)
        }

        return summaryMatrixList
                .map { groupedMap(it) }
                .map {
                    it.entries.map { entry -> SummaryMatrixModel(year = entry.key.first, month = entry.key.second, total = entry.value) }
                }
    }

    private fun groupByYearAndMonth(models: List<SummaryMatrixModel>): Map<Pair<Int, Int>, Double> {
        val map = HashMap<Pair<Int, Int>, Double>()
        models.forEach {
            if (map.containsKey(Pair(it.year, it.month))) {
                val storedTotal = map.getValue(Pair(it.year, it.month))
                map[Pair(it.year, it.month)] = storedTotal.plus(it.total)
            } else {
                map[Pair(it.year, it.month)] = it.total
            }
        }

        return map
    }


    fun createShareRecord(model: ShareJournalModel): Either<ServiceError, ShareJournalModel> {
        val createTransactionRecord = transactionService.createRecord(model.transactionDetails)
        val createShareRecord: (UUID, ShareJournalModel) -> Either<ServiceError, ShareJournalModel> = { id, journalModel ->
            shareService.createRecord(transactionId = id, model = journalModel)
        }

        return createTransactionRecord
                .flatMap { createShareRecord(it, model) }
    }

    fun editShareRecord(transactionId: UUID, model: ShareJournalModel): Either<ServiceError, Unit> {
        if (transactionId != model.transactionDetails.id) {
            return Either.Left(ServiceError.ValidationError("Transaction ID is not valid"))
        }

        return transactionService.editRecord(model.transactionDetails)
                .flatMap { shareService.editRecord(model) }
    }

    fun deleteShareRecord(accountId: UUID, transactionId: UUID): Either<ServiceError, Unit> {
        return shareService.getById(accountId, transactionId)
                .flatMap { shareService.deleteRecord(transactionId) }
                .flatMap { transactionService.deleteSettings(transactionId) }
                .flatMap { transactionService.deleteRecord(accountId, transactionId) }
    }


    fun createOptionRecord(model: OptionJournalModel): Either<ServiceError, OptionJournalModel> {
        val createTransactionRecord = transactionService.createRecord(model.transactionDetails)
        val createOptionRecord: (UUID, OptionJournalModel) -> Either<ServiceError, OptionJournalModel> = { id, journalModel ->
            optionService.createRecord(transactionId = id, model = journalModel)
        }

        return createTransactionRecord
                .flatMap { createOptionRecord(it, model) }
    }

    fun editOptionRecord(transactionId: UUID, model: OptionJournalModel): Either<ServiceError, Unit> {
        if (transactionId != model.transactionDetails.id) {
            return Either.Left(ServiceError.ValidationError("Transaction ID is not valid"))
        }

        return transactionService.editRecord(model.transactionDetails)
                .flatMap { optionService.editRecord(model) }
    }

    fun deleteOptionRecord(accountId: UUID, transactionId: UUID): Either<ServiceError, Unit> {
        return optionService.getById(accountId, transactionId)
                .flatMap { optionService.deleteRecord(transactionId) }
                .flatMap { transactionService.deleteSettings(transactionId) }
                .flatMap { transactionService.deleteRecord(accountId, transactionId) }
    }

    fun createDividendRecord(model: DividendJournalModel): Either<ServiceError, DividendJournalModel> {
        val createTransactionRecord = transactionService.createRecord(model.transactionDetails)
        val createDividendRecord: (UUID, DividendJournalModel) -> Either<ServiceError, DividendJournalModel> = { id, journalModel ->
            dividendService.createRecord(transactionId = id, model = journalModel)
        }

        return createTransactionRecord
                .flatMap { createDividendRecord(it, model) }
    }

    fun editDividendRecord(transactionId: UUID, model: DividendJournalModel): Either<ServiceError, Unit> {
        if (transactionId != model.transactionDetails.id) {
            return Either.Left(ServiceError.ValidationError("Transaction ID is not valid"))
        }

        return transactionService.editRecord(model.transactionDetails)
                .flatMap { dividendService.editRecord(model) }
    }

    fun deleteDividendRecord(accountId: UUID, transactionId: UUID): Either<ServiceError, Unit> {
        return dividendService.getById(accountId, transactionId)
                .flatMap { dividendService.deleteRecord(transactionId) }
                .flatMap { transactionService.deleteSettings(transactionId) }
                .flatMap { transactionService.deleteRecord(accountId, transactionId) }
    }
}
