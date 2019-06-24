package com.example.tradelog.api.resource;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.TransactionJournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class TransactionJournalResource {

    private static final Logger log = LoggerFactory.getLogger(TransactionJournalResource.class);

    private TransactionJournalService service;

    public TransactionJournalResource(TransactionJournalService transactionJournalService) {
        this.service = transactionJournalService;
    }

    @RequestMapping(value = "/{accountId}/symbols", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllTradedSymbols(@PathVariable("accountId") String accountId) throws TradeLogException {
        if (!RequestValidation.validateGetAllTradedSymbols(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        List<String> tradedSymbols = service.getAllTradedSymbols(accountId);

        if (tradedSymbols.isEmpty()) {
            throw new TradeLogException(ExceptionCode.TRADELOG_EMPTY);
        }

        return tradedSymbols;
    }
}
