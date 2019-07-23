package com.example.gateway.api.resource;

import com.example.gateway.api.converter.TradeLogModelConverter;
import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.exception.GatewayApiException;
import com.example.gateway.api.model.*;
import com.example.gateway.api.service.TradeLogService;
import com.example.tradelog.api.spec.model.TradeLogModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/tradelog", produces = "application/json")
public class TradeLogResource {

    private TradeLogService tradeLogService;

    public TradeLogResource(TradeLogService tradeLogService) {
        this.tradeLogService = tradeLogService;
    }


    @RequestMapping(value = "/all/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TradeLogGWModel getAllBySymbol(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol
            ) throws ExecutionException, InterruptedException {
        //todo validate input

        TradeLogModel tradeLogModel = tradeLogService.getAllBySymbol(accountId, symbol);
        return new TradeLogModelConverter().apply(tradeLogModel);
    }


    @RequestMapping(value = "/shares/data/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ShareDataGWModel getDataBySymbol(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol
    ) {
        return tradeLogService.getData(accountId, symbol);
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


    @RequestMapping(value = "/options/{symbol}/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOptionTrade(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol,
            @PathVariable(name = "id") String transactionId) {
        tradeLogService.deleteOptionTrade(accountId, transactionId, symbol);
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

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TradeSummaryGWModel> getSummary(@RequestHeader("accountId") String accountId) throws GatewayApiException {
        List<TradeSummaryGWModel> summaryModels = tradeLogService.getSummary(accountId);

        if (summaryModels == null || summaryModels.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TRADEJOURNAL_NO_SYMBOLS);
        }

        return summaryModels;
    }

    @RequestMapping(value = "/transaction/{transactionId}/{enabled}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateGroupOption(@RequestHeader("accountId") String accountId,
                                  @PathVariable("enabled") Boolean enabled,
                                  @PathVariable("transactionId") String transactionId) {
       tradeLogService.updateGroupOption(accountId, transactionId, enabled);
    }

    @RequestMapping(value = "/transaction/settings/bulk", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateBulkSettings(@RequestHeader("accountId") String accountId,
                                   @RequestBody List<TransactionSettingsGWModel> modelList) {
        tradeLogService.updateGroupSettings(accountId, modelList);
    }
}
