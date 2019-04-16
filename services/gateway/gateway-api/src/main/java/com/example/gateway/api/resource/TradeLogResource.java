package com.example.gateway.api.resource;

import com.example.gateway.api.converter.OptionJournalConverter;
import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.exception.GatewayApiException;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.service.TradeLogService;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/tradelog", produces = "application/json")
public class TradeLogResource {

    private static final Logger log = LoggerFactory.getLogger(TradeLogResource.class);

    private TradeLogService tradeLogService;

    public TradeLogResource(TradeLogService tradeLogService) {
        this.tradeLogService = tradeLogService;
    }


    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<OptionJournalGWModel> getAllByAccountId(@PathVariable(name = "accountId") String accountId) throws GatewayApiException {
        //todo validate input

        List<OptionJournalModel> modelList = tradeLogService.getAllByAccountId(accountId);

        if (modelList == null || modelList.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TRADEJOURNAL_EMPTY);
        }

        List<OptionJournalGWModel> gwModelList = modelList.stream()
                .map(OptionJournalConverter.gwModelConverter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return gwModelList;
    }

    @RequestMapping(value = "/{accountId}/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<OptionJournalGWModel> getAllByAccountAndSymbol(
            @PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol
            ) throws GatewayApiException {
        //todo validate input

        List<OptionJournalModel> modelList = tradeLogService.getAllByAccountAndSymbol(accountId, symbol);

        if (modelList == null || modelList.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TRADEJOURNAL_EMPTY);
        }

        List<OptionJournalGWModel> gwModelList = modelList.stream()
                .map(OptionJournalConverter.gwModelConverter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return gwModelList;
    }
}
