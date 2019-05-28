package com.example.tradelog.api.resource;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.JournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TradeLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class JournalResource {

    private static final Logger log = LoggerFactory.getLogger(JournalResource.class);

    private JournalService journalService;

    public JournalResource(JournalService journalService) {
        this.journalService = journalService;
    }


    @RequestMapping(value = "/{accountId}/symbols", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getAllTradedSymbols(@PathVariable("accountId") String accountId) throws TradeLogException {
        if (!RequestValidation.validateGetAllByAccountId(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Set<String> tradedSymbols = journalService.getAllTradedSymbols(accountId);

        if (tradedSymbols.isEmpty()) {
            throw new TradeLogException(ExceptionCode.TRADELOG_EMPTY);
        }

        return tradedSymbols;
    }


    @RequestMapping(value = "/{accountId}/trades/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TradeLogModel getAllBySymbol(
            @PathVariable("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (!RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        return journalService.getAllBySymbol(accountId, symbol);
    }



    @RequestMapping(value = "/{accountId}/tradesmul", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createOptionEntries(
            @RequestBody List<OptionJournalModel> models,
            @PathVariable("accountId") String accountId) throws TradeLogException {
        if (!RequestValidation.validatePostByAccountId(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        //TODO: validate model
        for (OptionJournalModel model : models) {
            Optional<OptionJournalModel> optionalModel = journalService.createOptionRecord(model);
            if (optionalModel.isEmpty()) {
                log.error("COULD NOT CREATE FOR: " + model.getStockSymbol() + " " + model.getPremium());
                throw new TradeLogException(ExceptionCode.CREATE_OPTION_FAILED);
            }
        }
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
            @PathVariable(name = "accountId") String accountId,
            @RequestBody ShareJournalModel model) throws TradeLogException {
        if (!RequestValidation.validatePostByAccountId(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        //TODO: validate model
        Optional<ShareJournalModel> optionalModel = journalService.createShareRecord(model);
        if (optionalModel.isEmpty()) {
            log.error("COULD NOT CREATE FOR: " + model.getSymbol());
            throw new TradeLogException(ExceptionCode.CREATE_SHARE_FAILED);
        }
        return optionalModel.get();
    }

    /**
     * Create OPTION trade record
     * @param accountId - account uuid
     * @param model - OptionJournalModel
     * @return created OptionJournalModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/{accountId}/option", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public OptionJournalModel createNewOptionTrade(
            @PathVariable("accountId") String accountId,
            @RequestBody OptionJournalModel model) throws TradeLogException {
        if (!RequestValidation.validatePostByAccountId(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        //TODO: validate model
        Optional<OptionJournalModel> optionalModel = journalService.createOptionRecord(model);
        if (optionalModel.isEmpty()) {
            throw new TradeLogException(ExceptionCode.CREATE_OPTION_FAILED);
        }
        return optionalModel.get();
    }
}
