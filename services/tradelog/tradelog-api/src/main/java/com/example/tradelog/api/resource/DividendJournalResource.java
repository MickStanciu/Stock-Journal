package com.example.tradelog.api.resource;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.DividendJournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/dividends", produces = "application/json")
public class DividendJournalResource {

    private static final Logger log = LoggerFactory.getLogger(DividendJournalResource.class);

    private DividendJournalService service;

    public DividendJournalResource(DividendJournalService service) {
        this.service = service;
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

        if (!RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        return service.getAllBySymbol(accountId, symbol);
    }

    //TODO: missing create dividend
    //TODO: missing update dividend
}
