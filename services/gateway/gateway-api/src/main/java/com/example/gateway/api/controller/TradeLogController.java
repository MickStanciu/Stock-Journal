package com.example.gateway.api.controller;

import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.exception.GatewayApiException;
import com.example.gateway.api.model.DividendGWModel;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogGWModel;
import com.example.gateway.api.model.TradeSummaryGWModel;
import com.example.gateway.api.model.TransactionSettingsGWModel;
import com.example.gateway.api.service.TradeLogService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/tradelog", produces = "application/json")
public class TradeLogController {

    private TradeLogService tradeLogService;

    public TradeLogController(TradeLogService tradeLogService) {
        this.tradeLogService = tradeLogService;
    }


    @RequestMapping(value = "/all/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TradeLogGWModel getAllBySymbol(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol) throws ExecutionException, InterruptedException {

        //todo validate input

        return tradeLogService.getAllBySymbol(accountId, symbol);
    }


    @RequestMapping(value = "/shares", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ShareJournalGWModel createNewShareTrade(
            @RequestHeader(name = "accountId") String accountId,
            @RequestBody ShareJournalGWModel model) {
        return tradeLogService.createShareTrade(accountId, model);
    }


    @RequestMapping(value = "/shares/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteShareTrade(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "id") String transactionId) {
        tradeLogService.deleteShareTrade(accountId, transactionId);
    }


    @RequestMapping(value = "/options", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public OptionJournalGWModel createNewOptionTrade(
            @RequestHeader(name = "accountId") String accountId,
            @RequestBody OptionJournalGWModel model) {
        return tradeLogService.createOptionTrade(accountId, model);
    }


    @RequestMapping(value = "/options/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteOptionTrade(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "id") String transactionId) {
        tradeLogService.deleteOptionTrade(accountId, transactionId);
    }


    @RequestMapping(value = "/dividends", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public DividendGWModel createDividendRecord(
            @RequestHeader(name = "accountId") String accountId,
            @RequestBody DividendGWModel model) {
        return tradeLogService.createDividendRecord(accountId, model);
    }


    @RequestMapping(value = "/dividends/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteDividendRecord(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "id") String transactionId) {
        tradeLogService.deleteDividendRecord(accountId, transactionId);
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

    @RequestMapping(value = "/transaction/settings/bulk", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateBulkSettings(@RequestHeader("accountId") String accountId,
                                   @RequestBody List<TransactionSettingsGWModel> modelList) {
        tradeLogService.updateGroupSettings(accountId, modelList);
    }

    @RequestMapping(value = "/transaction/settings/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateSettings(@RequestHeader("accountId") String accountId,
                               @PathVariable(name = "id") String transactionId,
                               @RequestBody TransactionSettingsGWModel model) {
        tradeLogService.updateGroupSetting(accountId, transactionId, model);
    }
}
