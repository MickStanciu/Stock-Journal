package com.example.web.resource;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeSummaryGWModel;
import com.example.web.model.TradeLogModel;
import com.example.web.service.TradeJournalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AjaxResource {

    private TradeJournalService tradeJournalService;

    public AjaxResource(TradeJournalService tradeJournalService) {
        this.tradeJournalService = tradeJournalService;
    }


    @RequestMapping(value = "/tradelog/{accountId}/trades/{symbol}", method = RequestMethod.GET)
    public TradeLogModel getAllTradesBySymbol(@PathVariable(name = "accountId") String accountId, @PathVariable(name = "symbol") String symbol) {
        TradeLogModel model = tradeJournalService.getAllTradesBySymbol(accountId, symbol);
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
}
