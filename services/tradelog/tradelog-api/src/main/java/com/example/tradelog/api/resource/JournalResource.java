package com.example.tradelog.api.resource;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.JournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class JournalResource {

    private static final Logger log = LoggerFactory.getLogger(JournalResource.class);

    private JournalService journalService;

    public JournalResource(JournalService journalService) {
        this.journalService = journalService;
    }

//    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public List<OptionJournalModel> getAllByAccountId(@PathVariable("accountId") String accountId) throws TradeLogException {
//        if (!RequestValidation.validateGetAllByAccountId(accountId)) {
//            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
//        }
//
//        List<OptionJournalModel> tradeLogs = journalService.getAllByAccountId(accountId);
//
//        if (tradeLogs.isEmpty()) {
//            throw new TradeLogException(ExceptionCode.TRADELOG_EMPTY);
//        }
//
//        return tradeLogs;
//    }


    @RequestMapping(value = "/{accountId}/symbols", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSymbolByAccountId(@PathVariable("accountId") String accountId) throws TradeLogException {
        if (!RequestValidation.validateGetAllByAccountId(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        List<String> tradedSymbols = journalService.getAllSymbolsByAccount(accountId);

        if (tradedSymbols.isEmpty()) {
            throw new TradeLogException(ExceptionCode.TRADELOG_EMPTY);
        }

        return tradedSymbols;
    }


    @RequestMapping(value = "/{accountId}/trades/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<OptionJournalModel> getAllBySymbolAndAccountId(
            @PathVariable("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {
        if (!RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        List<OptionJournalModel> tradeLogs = journalService.getAllBySymbolAndAccount(accountId, symbol);

        if (tradeLogs.isEmpty()) {
            throw new TradeLogException(ExceptionCode.TRADELOG_EMPTY);
        }

        return tradeLogs;
    }


    @RequestMapping(value = "/{accountId}/trades", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public OptionJournalModel createOptionEntry(
            @RequestBody OptionJournalModel model,
            @PathVariable("accountId") String accountId) throws TradeLogException {
        if (!RequestValidation.validatePostByAccountId(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        //TODO: validate model
        Optional<OptionJournalModel> optionJournalModel = journalService.createOptionRecord(model);
        if (optionJournalModel.isEmpty()) {
            throw new TradeLogException(ExceptionCode.CREATE_OPTION_FAILED);
        }
        return optionJournalModel.get();
    }
}
