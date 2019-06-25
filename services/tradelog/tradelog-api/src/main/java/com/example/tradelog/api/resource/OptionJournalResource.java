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

        if (!RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
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
    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public OptionJournalModel createNewOptionTrade(
            @RequestHeader("accountId") String accountId,
            @RequestBody OptionJournalModel model) throws TradeLogException {

        if (!RequestValidation.validateCreateNewOptionTrade(accountId, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<OptionJournalModel> optionalModel = service.createOptionRecord(model);
        if (optionalModel.isEmpty()) {
            throw new TradeLogException(ExceptionCode.CREATE_OPTION_FAILED);
        }
        return optionalModel.get();
    }

    //TODO: missing update option
}
