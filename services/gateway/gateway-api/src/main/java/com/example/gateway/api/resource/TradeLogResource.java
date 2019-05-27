package com.example.gateway.api.resource;

import com.example.gateway.api.converter.TradeLogModelConverter;
import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.exception.GatewayApiException;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogModelGW;
import com.example.gateway.api.service.TradeLogService;
import com.example.tradelog.api.spec.model.TradeLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tradelog", produces = "application/json")
public class TradeLogResource {

    private static final Logger log = LoggerFactory.getLogger(TradeLogResource.class);

    private TradeLogService tradeLogService;

    public TradeLogResource(TradeLogService tradeLogService) {
        this.tradeLogService = tradeLogService;
    }


    @RequestMapping(value = "/{accountId}/trades/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TradeLogModelGW getAllBySymbol(
            @PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol
            ) {
        //todo validate input

        TradeLogModel tradeLogModel = tradeLogService.getAllBySymbol(accountId, symbol);
        return new TradeLogModelConverter().apply(tradeLogModel);
    }

    @RequestMapping(value = "/{accountId}/symbols", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllTradedSymbols(@PathVariable(name = "accountId") String accountId) throws GatewayApiException {
        //todo validate input

        List<String> tradedSymbols = tradeLogService.getAllTradedSymbols(accountId);

        if (tradedSymbols == null || tradedSymbols.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TRADEJOURNAL_NO_SYMBOLS);
        }

        return tradedSymbols;
    }

    @PostMapping(value = "/{accountId}/share")
    @ResponseStatus(HttpStatus.OK)
    public ShareJournalGWModel createNewShareTrade(
            @PathVariable(name = "accountId") String accountId,
            @RequestBody ShareJournalGWModel model) {
        System.out.println("Received to create: " + model.getSymbol());
        return tradeLogService.createShareTrade(accountId, model);
    }
}
