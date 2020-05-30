package com.example.tradelog.api.core.facade

import com.example.common.service.ServiceError
import com.example.common.types.Either
import com.example.common.types.Either.Companion.bind
import com.example.tradelog.api.core.model.*
import com.example.tradelog.api.core.service.DividendJournalService
import com.example.tradelog.api.core.service.OptionJournalService
import com.example.tradelog.api.core.service.ShareJournalService
import com.example.tradelog.api.core.service.TransactionService
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class JournalFacade(private val transactionService: TransactionService,
                    private val shareService: ShareJournalService,
                    private val optionService: OptionJournalService,
                    private val dividendService: DividendJournalService
) {

    fun getSummary(accountId: String): Either<ServiceError, List<TradeSummaryModel>> {
        //TODO: do parallel call
        //TODO: rewrite it
        val shareSummaries = shareService.getSummaries(accountId).rightOrNull() ?: return Either.Left(ServiceError.DataAccessError("Cannot get share summaries"))
        val optionSummaries = optionService.getSummaries(accountId).rightOrNull() ?: return Either.Left(ServiceError.DataAccessError("Cannot get option summaries"))
        val dividendSummaries = dividendService.getSummaries(accountId).rightOrNull() ?: return Either.Left(ServiceError.DataAccessError("Cannot get dividend summaries"))

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

    fun getSummaryMatrix(accountId: String, portfolioId: String): Either<ServiceError, List<SummaryMatrixModel>> {
        val summaryMatrixList  = transactionService.getSummaryMatrix(accountId, portfolioId)

        val groupedMap: (List<SummaryMatrixModel>) -> Map<Pair<Int, Int>, Double> = {
            models -> groupByYearAndMonth(models)
        }

        return summaryMatrixList
                .mapRight { groupedMap(it) }
                .mapRight {
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
        val createShareRecord: (String, ShareJournalModel) -> Either<ServiceError, ShareJournalModel> = { id, journalModel ->
            shareService.createRecord(transactionId = id, model = journalModel)
        }

        return createTransactionRecord
                .bind { createShareRecord(it, model) }
    }

    fun editShareRecord(transactionId: String, model: ShareJournalModel): Either<ServiceError, Unit> {
        if (transactionId != model.transactionDetails.id) {
            return Either.Left(ServiceError.ValidationError("Transaction ID is not valid"))
        }

        return transactionService.editRecord(model.transactionDetails)
                .bind { shareService.editRecord(model) }
    }

    fun deleteShareRecord(accountId: String, transactionId: String): Either<ServiceError, Unit> {
        return shareService.getById(accountId, transactionId)
                .bind { shareService.deleteRecord(transactionId) }
                .bind { transactionService.deleteSettings(transactionId) }
                .bind { transactionService.deleteRecord(accountId, transactionId) }
    }


    fun createOptionRecord(model: OptionJournalModel): Either<ServiceError, OptionJournalModel> {
        val createTransactionRecord = transactionService.createRecord(model.transactionDetails)
        val createOptionRecord: (String, OptionJournalModel) -> Either<ServiceError, OptionJournalModel> = { id, journalModel ->
            optionService.createRecord(transactionId = id, model = journalModel)
        }

        return createTransactionRecord
                .bind { createOptionRecord(it, model) }
    }

    fun editOptionRecord(transactionId: String, model: OptionJournalModel): Either<ServiceError, Unit> {
        if (transactionId != model.transactionDetails.id) {
            return Either.Left(ServiceError.ValidationError("Transaction ID is not valid"))
        }

        return transactionService.editRecord(model.transactionDetails)
                .bind { optionService.editRecord(model) }
    }

    fun deleteOptionRecord(accountId: String, transactionId: String): Either<ServiceError, Unit> {
        return optionService.getById(accountId, transactionId)
                .bind { optionService.deleteRecord(transactionId) }
                .bind { transactionService.deleteSettings(transactionId) }
                .bind { transactionService.deleteRecord(accountId, transactionId) }
    }

    fun createDividendRecord(model: DividendJournalModel): Either<ServiceError, DividendJournalModel> {
        val createTransactionRecord = transactionService.createRecord(model.transactionDetails)
        val createDividendRecord: (String, DividendJournalModel) -> Either<ServiceError, DividendJournalModel> = { id, journalModel ->
            dividendService.createRecord(transactionId = id, model = journalModel)
        }

        return createTransactionRecord
                .bind { createDividendRecord(it, model) }
    }

    fun editDividendRecord(transactionId: String, model: DividendJournalModel): Either<ServiceError, Unit> {
        if (transactionId != model.transactionDetails.id) {
            return Either.Left(ServiceError.ValidationError("Transaction ID is not valid"))
        }

        return transactionService.editRecord(model.transactionDetails)
                .bind { dividendService.editRecord(model) }
    }

    fun deleteDividendRecord(accountId: String, transactionId: String): Either<ServiceError, Unit> {
        return dividendService.getById(accountId, transactionId)
                .bind { dividendService.deleteRecord(transactionId) }
                .bind { transactionService.deleteSettings(transactionId) }
                .bind { transactionService.deleteRecord(accountId, transactionId) }
    }
}
