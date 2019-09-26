package com.example.tradelog.api.controller;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.facade.JournalFacade;
import com.example.tradelog.api.service.DividendJournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.DividendTransactionsResponse;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/dividends", produces = "application/json")
public class DividendJournalController {

    private static final Logger log = LoggerFactory.getLogger(DividendJournalController.class);

    private JournalFacade journalFacade;

    public DividendJournalController(JournalFacade journalFacade) {
        this.journalFacade = journalFacade;
    }


    /**
     * Queries the list of dividends for given symbol
     * @param symbol - ex 'CVS'
     * @return list of DividendJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = {"/{symbol}", "/{symbol}/"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public DividendTransactionsResponse getAllBySymbol(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (!RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        return new DividendTransactionsResponse.Builder()
                .withDividendItems(journalFacade.getAllDividendsBySymbol(accountId, symbol))
                .build();
    }


    /**
     * Create DIVIDEND record
     * @param accountId - account uuid
     * @param model - DividendJournalModel
     * @return created DividendJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public DividendJournalModel createNewDividendRecord(
            @RequestHeader(name = "accountId") String accountId,
            @RequestBody DividendJournalModel model) throws TradeLogException {

        if (!RequestValidation.validateCreateNewDividendRecord(accountId, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<DividendJournalModel> optionalModel = journalFacade.createDividendRecord(model);
        if (optionalModel.isEmpty()) {
            log.error("COULD NOT CREATE FOR: " + model.getTransactionDetails().getSymbol());
            throw new TradeLogException(ExceptionCode.CREATE_DIVIDEND_FAILED);
        }
        return optionalModel.get();
    }


    /**
     * Delete DIVIDEND trade record
     * @param accountId - account uuid
     * @param transactionId -
     * @throws TradeLogException -
     */
    @RequestMapping(value = {"/{transactionId}", "/{transactionId}/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteDividendRecord(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable("transactionId") String transactionId) throws TradeLogException {

        if (!RequestValidation.validateDeleteDividendRecord(accountId, transactionId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        if (!journalFacade.deleteDividendRecord(accountId, transactionId)) {
            log.error("COULD NOT DELETE FOR tID: {}", transactionId);
            throw new TradeLogException(ExceptionCode.DELETE_DIVIDEND_FAILED);
        }
    }

    //TODO: missing update dividend

}
