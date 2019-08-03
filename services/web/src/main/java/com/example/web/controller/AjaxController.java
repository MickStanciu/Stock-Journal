package com.example.web.controller;

import com.example.gateway.api.model.*;
import com.example.web.service.TradeJournalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AjaxController {

    private TradeJournalService tradeJournalService;

    public AjaxController(TradeJournalService tradeJournalService) {
        this.tradeJournalService = tradeJournalService;
    }


    @RequestMapping(value = "/tradelog/{accountId}/trades/{symbol}", method = RequestMethod.GET)
    public TradeLogGWModel getAllTradesBySymbol(@PathVariable(name = "accountId") String accountId, @PathVariable(name = "symbol") String symbol) {
        TradeLogGWModel model = tradeJournalService.getAllTradesBySymbol(accountId, symbol);
        //todo validate input
        //todo validate exceptions when GatewayAPI is down
        return model;
    }

    @RequestMapping(value = "/tradelog/{accountId}/symbols", method = RequestMethod.GET)
    public List<String> getUniqueSymbols(@PathVariable(name = "accountId") String accountId) {
        List<String> uniqueSymbols = tradeJournalService.getUniqueSymbols(accountId);
        //todo validate input
        //todo validate exceptions when GatewayAPI is down
        return uniqueSymbols;
    }

    @RequestMapping(value = "/tradelog/{accountId}/summary", method = RequestMethod.GET)
    public List<TradeSummaryGWModel> getSummary(@PathVariable(name = "accountId") String accountId) {
        List<TradeSummaryGWModel> summary = tradeJournalService.getSummary(accountId);
        //todo validate input
        //todo validate exceptions when GatewayAPI is down
        return summary;
    }

    @RequestMapping(value = "/tradelog/{accountId}/share/data/{symbol}", method = RequestMethod.GET)
    public ShareDataGWModel getShareData(
            @PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol
    ) {
        return tradeJournalService.getShareData(accountId, symbol);
    }


    @RequestMapping(value = "/tradelog/{accountId}/share", method = RequestMethod.POST)
    public ShareJournalGWModel createNewShareTrade(
            @PathVariable(name = "accountId") String accountId,
            @RequestBody ShareJournalGWModel model) {
        return tradeJournalService.createShareTrade(accountId, model);
    }

    @RequestMapping(value = "/tradelog/{accountId}/share/{symbol}/{id}", method = RequestMethod.DELETE)
    public String deleteShareTrade(
            @PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol,
            @PathVariable(name = "id") String transactionId
    ) {
        tradeJournalService.deleteShareTrade(accountId, symbol, transactionId);
        return "{}";
    }

    @RequestMapping(value = "/tradelog/{accountId}/option", method = RequestMethod.POST)
    public OptionJournalGWModel createNewOptionTrade(
            @PathVariable(name = "accountId") String accountId,
            @RequestBody OptionJournalGWModel model) {
        return tradeJournalService.createOptionTrade(accountId, model);
    }

    @RequestMapping(value = "/tradelog/{accountId}/option/{symbol}/{id}", method = RequestMethod.DELETE)
    public String deleteOptionTrade(
            @PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol,
            @PathVariable(name = "id") String transactionId
    ) {
        tradeJournalService.deleteOptionTrade(accountId, symbol, transactionId);
        return "{}";
    }

    @RequestMapping(value = "/tradelog/{accountId}/dividend", method = RequestMethod.POST)
    public DividendGWModel createDividendRecord(
            @PathVariable(name = "accountId") String accountId,
            @RequestBody DividendGWModel model) {
        return tradeJournalService.createDividendRecord(accountId, model);
    }


    @RequestMapping(value = "/tradelog/{accountId}/settings/bulk", method = RequestMethod.PUT)
    public String updateSettings(
            @PathVariable(name = "accountId") String accountId,
            @RequestBody List<TransactionSettingsGWModel> transactionSettingsGWModelList
    ) {
        tradeJournalService.updateSettings(accountId, transactionSettingsGWModelList);
        return "{}";
    }
}
