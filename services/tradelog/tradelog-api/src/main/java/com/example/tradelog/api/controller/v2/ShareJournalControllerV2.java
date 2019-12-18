package com.example.tradelog.api.controller.v2;

import com.example.tradelog.api.converter.protobuf.ShareJournalModelConverter;
import com.example.tradelog.api.facade.JournalFacade;
import com.example.tradelog.api.rest.exception.ExceptionCode;
import com.example.tradelog.api.rest.exception.TradeLogException;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v2/shares", produces = "application/json")
public class ShareJournalControllerV2 {

    private static final Logger log = LoggerFactory.getLogger(ShareJournalControllerV2.class);

    private JournalFacade facade;

    public ShareJournalControllerV2(JournalFacade facade) {
        this.facade = facade;
    }


    @RequestMapping(value = {"/{symbol}", "/{symbol}/"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public com.example.tradelog.api.proto3.model.ShareTransactionsResponse getAllBySymbolProto(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (!RequestValidation.validateGetAccountAndSymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        List<com.example.tradelog.api.proto3.model.ShareJournalModel> items =
                facade.getAllSharesBySymbol(accountId, symbol).stream()
                        .map(ShareJournalModelConverter.toProto)
                        .collect(Collectors.toList());

        return com.example.tradelog.api.proto3.model.ShareTransactionsResponse.newBuilder()
                .addAllShareItems(items)
                .build();
    }
}
