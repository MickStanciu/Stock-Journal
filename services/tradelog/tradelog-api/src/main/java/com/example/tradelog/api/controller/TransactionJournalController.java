package com.example.tradelog.api.controller;

import com.example.tradelog.api.core.model.TransactionSettingsModel;
import com.example.tradelog.api.facade.JJournalFacade;
import com.example.tradelog.api.rest.exception.ExceptionCode;
import com.example.tradelog.api.rest.exception.TradeLogException;
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

    private JJournalFacade facade;


    public TransactionJournalController(JJournalFacade journalFacade) {
        this.facade = journalFacade;
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
