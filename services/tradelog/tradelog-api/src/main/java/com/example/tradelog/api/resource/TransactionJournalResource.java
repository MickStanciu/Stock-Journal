package com.example.tradelog.api.resource;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.facade.JournalFacade;
import com.example.tradelog.api.service.TransactionJournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionOptionsModel;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/transactions", produces = "application/json")
public class TransactionJournalResource {

    private static final Logger log = LoggerFactory.getLogger(TransactionJournalResource.class);

    private JournalFacade facade;


    public TransactionJournalResource(JournalFacade journalFacade) {
        this.facade = journalFacade;
    }


    @RequestMapping(value = "/symbols", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllTradedSymbols(@RequestHeader("accountId") String accountId) throws TradeLogException {

        if (!RequestValidation.validateGetAllTradedSymbols(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        List<String> tradedSymbols = facade.getAllTradedSymbols(accountId);

        if (tradedSymbols.isEmpty()) {
            throw new TradeLogException(ExceptionCode.TRADELOG_EMPTY);
        }

        return tradedSymbols;
    }


    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TradeSummaryModel> getSummary(@RequestHeader("accountId") String accountId) throws TradeLogException {

        if (!RequestValidation.validateGetSummary(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        List<TradeSummaryModel> summaryList = facade.getSummary(accountId);

        if (summaryList.isEmpty()) {
            throw new TradeLogException(ExceptionCode.TRADELOG_EMPTY);
        }

        return summaryList;
    }


    @RequestMapping(value = "/options/{transactionId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateOptions(@RequestHeader("accountId") String accountId,
                              @PathVariable("transactionId") String transactionId,
                              @RequestBody TransactionOptionsModel model) throws TradeLogException {

        if (!RequestValidation.validateUpdateOptions(accountId, transactionId, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        if (!facade.updateOptions(accountId, transactionId, model)) {
            log.error("COULD NOT UPDATE OPTIONS FOR tID: {}", transactionId);
            throw new TradeLogException(ExceptionCode.UPDATE_TRANSACTION_OPTIONS_FAILED);
        }
    }
}
