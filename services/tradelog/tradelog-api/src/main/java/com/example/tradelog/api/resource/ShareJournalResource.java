package com.example.tradelog.api.resource;

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
@RequestMapping(value = "/api/v1", produces = "application/json")
public class ShareJournalResource {

    private static final Logger log = LoggerFactory.getLogger(ShareJournalResource.class);

    private ShareJournalService service;

    public ShareJournalResource(ShareJournalService service) {
        this.service = service;
    }


    /**
     * Queries the list of shares for given symbol
     * @param symbol - ex 'CVS'
     * @return list of ShareJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/shares/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ShareJournalModel> getAllBySymbol(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (!RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
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
    @PostMapping(value = "/{accountId}/share")
    @ResponseStatus(HttpStatus.OK)
    public ShareJournalModel createNewShareTrade(
            @RequestHeader(name = "accountId") String accountId,
            @RequestBody ShareJournalModel model) throws TradeLogException {

        if (!RequestValidation.validateCreateNewShareTrade(accountId, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<ShareJournalModel> optionalModel = service.createShareRecord(model);
        if (optionalModel.isEmpty()) {
            log.error("COULD NOT CREATE FOR: " + model.getTransactionModel().getSymbol());
            throw new TradeLogException(ExceptionCode.CREATE_SHARE_FAILED);
        }
        return optionalModel.get();
    }

    //TODO: missing update share



}
