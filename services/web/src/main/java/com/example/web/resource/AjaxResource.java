package com.example.web.resource;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
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


    @RequestMapping(value = "/tradelog/{accountId}/trades/{symbol}")
    public TradeLogModel getAllTradesBySymbol(@PathVariable(name = "accountId") String accountId, @PathVariable(name = "symbol") String symbol) {
        TradeLogModel model = tradeJournalService.getAllTradesBySymbol(accountId, symbol);
        //todo validate input
        //todo validate exceptions when GatewayAPI is down
        return model;
    }

    @RequestMapping(value = "/tradelog/{accountId}/symbols")
    public List<String> getUniqueSymbols(@PathVariable(name = "accountId") String accountId) {
        List<String> uniqueSymbols = tradeJournalService.getUniqueSymbols(accountId);
        //todo validate input
        //todo validate exceptions when GatewayAPI is down
        return uniqueSymbols;
    }

    @PostMapping(value = "/tradelog/{accountId}/share")
    public ShareJournalGWModel createNewShareTrade(
            @PathVariable(name = "accountId") String accountId,
            @RequestBody ShareJournalGWModel model) {
        return tradeJournalService.createShareTrade(accountId, model);
    }

    @PostMapping(value = "/tradelog/{accountId}/option")
    public OptionJournalGWModel createNewOptionTrade(
            @PathVariable(name = "accountId") String accountId,
            @RequestBody OptionJournalGWModel model) {
        return tradeJournalService.createOptionTrade(accountId, model);
    }
}