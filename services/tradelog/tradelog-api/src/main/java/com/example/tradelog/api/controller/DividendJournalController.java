package com.example.tradelog.api.controller;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.facade.JournalFacade;
import com.example.tradelog.api.service.DividendJournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.DividendJournalModel;
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
    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<DividendJournalModel> getAllBySymbol(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        return journalFacade.getAllDividendsBySymbol(accountId, symbol);
    }


    /**
     * Create DIVIDEND record
     * @param accountId - account uuid
     * @param model - DividendJournalModel
     * @return created DividendJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/{symbol}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public DividendJournalModel createNewDividendRecord(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable("symbol") String symbol,
            @RequestBody DividendJournalModel model) throws TradeLogException {

        if (RequestValidation.validateCreateNewDividendRecord(accountId, symbol, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<DividendJournalModel> optionalModel = journalFacade.createDividendRecord(model);
        if (optionalModel.isEmpty()) {
            log.error("COULD NOT CREATE FOR: " + model.getTransactionDetails().getSymbol());
            throw new TradeLogException(ExceptionCode.CREATE_DIVIDEND_FAILED);
        }
        return optionalModel.get();
    }

    //TODO: missing update dividend
    //TODO: missing delete dividend
}
