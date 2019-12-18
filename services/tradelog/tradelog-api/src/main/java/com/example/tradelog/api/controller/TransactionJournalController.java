package com.example.tradelog.api.controller;

import com.example.tradelog.api.facade.JournalFacade;
import com.example.tradelog.api.rest.exception.ExceptionCode;
import com.example.tradelog.api.rest.exception.TradeLogException;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/transactions", produces = "application/json")
public class TransactionJournalController {

    private static final Logger log = LoggerFactory.getLogger(TransactionJournalController.class);

    private JournalFacade facade;


    public TransactionJournalController(JournalFacade journalFacade) {
        this.facade = journalFacade;
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
                              @RequestBody TransactionSettingsModel model) throws TradeLogException {

        if (!RequestValidation.validateUpdateOptions(accountId, transactionId, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        //todo: validate account id against transaction?

        if (!facade.updateOptions(model)) {
            log.error("COULD NOT UPDATE OPTIONS FOR tID: {}", transactionId);
            throw new TradeLogException(ExceptionCode.UPDATE_TRANSACTION_OPTIONS_FAILED);
        }
    }

    @RequestMapping(value = "/settings/bulk", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateSettingsBulk(@RequestHeader("accountId") String accountId,
                                   @RequestBody List<TransactionSettingsModel> models) throws TradeLogException {

        if (!RequestValidation.validateUpdateSettingsBulk(accountId, models)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        facade.updateSettingsBulk(models);
    }

}
