package com.example.gateway.api.resource;

import com.example.gateway.api.converter.TradeLogModelConverter;
import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.exception.GatewayApiException;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogModelGW;
import com.example.gateway.api.service.TradeLogService;
import com.example.tradelog.api.spec.model.TradeLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/tradelog", produces = "application/json")
public class TradeLogResource {

    private static final Logger log = LoggerFactory.getLogger(TradeLogResource.class);

    private TradeLogService tradeLogService;

    public TradeLogResource(TradeLogService tradeLogService) {
        this.tradeLogService = tradeLogService;
    }


    @RequestMapping(value = "/all/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TradeLogModelGW getAllBySymbol(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol
            ) throws ExecutionException, InterruptedException {
        //todo validate input

        TradeLogModel tradeLogModel = tradeLogService.getAllBySymbol(accountId, symbol);
        return new TradeLogModelConverter().apply(tradeLogModel);
    }


    @RequestMapping(value = "/shares", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ShareJournalGWModel createNewShareTrade(
            @RequestHeader(name = "accountId") String accountId,
            @RequestBody ShareJournalGWModel model) {
        return tradeLogService.createShareTrade(accountId, model);
    }

    @RequestMapping(value = "/shares/{symbol}/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteShareTrade(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol,
            @PathVariable(name = "id") String transactionId) {
        tradeLogService.deleteShareTrade(accountId, transactionId, symbol);
    }


    @RequestMapping(value = "/options", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public OptionJournalGWModel createNewOptionTrade(
            @RequestHeader(name = "accountId") String accountId,
            @RequestBody OptionJournalGWModel model) {
        return tradeLogService.createOptionTrade(accountId, model);
    }


    @RequestMapping(value = "/symbols", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllTradedSymbols(@RequestHeader(name = "accountId") String accountId) throws GatewayApiException {
        //todo validate input

        List<String> tradedSymbols = tradeLogService.getAllTradedSymbols(accountId);

        if (tradedSymbols == null || tradedSymbols.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TRADEJOURNAL_NO_SYMBOLS);
        }

        return tradedSymbols;
    }
}
