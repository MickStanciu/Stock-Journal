package com.example.tradelog.api.resource;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.OptionJournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/options", produces = "application/json")
public class OptionJournalResource {

    private static final Logger log = LoggerFactory.getLogger(OptionJournalResource.class);

    private OptionJournalService service;

    public OptionJournalResource(OptionJournalService service) {
        this.service = service;
    }


    /**
     * Queries the list of options for given symbol
     * @param symbol - ex 'CVS'
     * @return list of OptionJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<OptionJournalModel> getAllBySymbol(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        return service.getAllBySymbol(accountId, symbol);
    }


    /**
     * Create OPTION trade record
     * @param accountId - account uuid
     * @param model - OptionJournalModel
     * @return created OptionJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/{symbol}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public OptionJournalModel createNewOptionTrade(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol,
            @RequestBody OptionJournalModel model) throws TradeLogException {

        if (!RequestValidation.validateCreateNewOptionTrade(accountId, symbol, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<OptionJournalModel> optionalModel = service.createOptionRecord(model);
        if (optionalModel.isEmpty()) {
            throw new TradeLogException(ExceptionCode.CREATE_OPTION_FAILED);
        }
        return optionalModel.get();
    }


    /**
     * Delete OPTION trade record
     * @param accountId - account uuid
     * @param symbol -
     * @param transactionId -
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/{symbol}/{transactionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOptionTrade(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable("symbol") String symbol,
            @PathVariable("transactionId") String transactionId) throws TradeLogException {

        if (!RequestValidation.validateDeleteOptionTrade(accountId, transactionId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        if (!service.deleteOptionRecord(accountId, transactionId, symbol)) {
            log.error("COULD NOT DELETE FOR tID: {}", transactionId);
            throw new TradeLogException(ExceptionCode.DELETE_OPTION_FAILED);
        }
    }


    //TODO: missing update option
}
