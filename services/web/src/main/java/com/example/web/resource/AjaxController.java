package com.example.web.resource;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.web.service.TradeJournalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class AjaxController {

    private TradeJournalService tradeJournalService;

    public AjaxController(TradeJournalService tradeJournalService) {
        this.tradeJournalService = tradeJournalService;
    }


    @RequestMapping(value = "/tradelog/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<List<OptionJournalGWModel>> getAllByAccountId(@PathVariable(name = "accountId") String accountId) {
        List<OptionJournalGWModel> gwModelList = tradeJournalService.getAllByAccountId("d79ec11a-2011-4423-ba01-3af8de0a3e14");
//        gwModelList.forEach(p -> System.out.println(p.getStockSymbol() + "  " + p.getPremium()));
        //todo validate input
        //todo validate exceptions when GatewayAPI is down

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(gwModelList);
    }


}
