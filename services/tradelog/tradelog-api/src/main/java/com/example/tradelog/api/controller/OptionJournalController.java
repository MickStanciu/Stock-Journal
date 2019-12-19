package com.example.tradelog.api.controller;

import com.example.tradelog.api.core.model.OptionJournalModel;
import com.example.tradelog.api.core.xmodel.OptionTransactionsResponse;
import com.example.tradelog.api.facade.JournalFacade;
import com.example.tradelog.api.rest.exception.ExceptionCode;
import com.example.tradelog.api.rest.exception.TradeLogException;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/options", produces = "application/json")
public class OptionJournalController {

    private static final Logger log = LoggerFactory.getLogger(OptionJournalController.class);

    private JournalFacade facade;

    public OptionJournalController(JournalFacade facade) {
        this.facade = facade;
    }


    /**
     * Queries the list of options for given symbol
     * @param symbol - ex 'CVS'
     * @return list of OptionJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = {"/{symbol}", "/{symbol}/"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public OptionTransactionsResponse getAllBySymbol(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (!RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        return new OptionTransactionsResponse(facade.getAllOptionsBySymbol(accountId, symbol));
    }


    /**
     * Create OPTION trade record
     * @param accountId - account uuid
     * @param model - OptionJournalModel
     * @return created OptionJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public OptionJournalModel createNewOptionTrade(
            @RequestHeader("accountId") String accountId,
            @RequestBody OptionJournalModel model) throws TradeLogException {

        if (!RequestValidation.validateCreateNewOptionTrade(accountId, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<OptionJournalModel> optionalModel = facade.createOptionRecord(model);
        if (optionalModel.isEmpty()) {
            throw new TradeLogException(ExceptionCode.CREATE_OPTION_FAILED);
        }
        return optionalModel.get();
    }


    /**
     * Delete OPTION trade record
     * @param accountId - account uuid
     * @param transactionId -
     * @throws TradeLogException -
     */
    @RequestMapping(value = {"/{transactionId}", "/{transactionId}/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOptionTrade(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable("transactionId") String transactionId) throws TradeLogException {

        if (!RequestValidation.validateDeleteOptionTrade(accountId, transactionId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        if (!facade.deleteOptionRecord(accountId, transactionId)) {
            log.error("COULD NOT DELETE FOR tID: {}", transactionId);
            throw new TradeLogException(ExceptionCode.DELETE_OPTION_FAILED);
        }
    }


    //TODO: missing update option
}
