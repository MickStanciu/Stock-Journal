package com.example.tradelog.api.controller;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.ShareJournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/shares", produces = "application/json")
public class ShareJournalController {

    private static final Logger log = LoggerFactory.getLogger(ShareJournalController.class);

    private ShareJournalService service;

    public ShareJournalController(ShareJournalService service) {
        this.service = service;
    }


    /**
     * Queries the list of shares for given symbol
     * @param symbol - ex 'CVS'
     * @return list of ShareJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ShareJournalModel> getAllBySymbol(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        return service.getAllBySymbol(accountId, symbol);
    }


    /**
     * Create SHARE trade record
     * @param accountId - account uuid
     * @param model - ShareJournalModel
     * @return created ShareJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/{symbol}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ShareJournalModel createNewShareTrade(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable("symbol") String symbol,
            @RequestBody ShareJournalModel model) throws TradeLogException {

        if (!RequestValidation.validateCreateNewShareTrade(accountId, symbol, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<ShareJournalModel> optionalModel = service.createShareRecord(model);
        if (optionalModel.isEmpty()) {
            log.error("COULD NOT CREATE FOR: " + model.getTransactionDetails().getSymbol());
            throw new TradeLogException(ExceptionCode.CREATE_SHARE_FAILED);
        }
        return optionalModel.get();
    }


    /**
     * Delete SHARE trade record
     * @param accountId - account uuid
     * @param symbol -
     * @param transactionId -
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/{symbol}/{transactionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteShareTrade(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable("symbol") String symbol,
            @PathVariable("transactionId") String transactionId) throws TradeLogException {

        if (!RequestValidation.validateDeleteShareTrade(accountId, transactionId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        if (!service.deleteShareRecord(accountId, transactionId, symbol)) {
            log.error("COULD NOT DELETE FOR tID: {}", transactionId);
            throw new TradeLogException(ExceptionCode.DELETE_SHARE_FAILED);
        }
    }


    //TODO: missing update share
}
