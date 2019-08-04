package com.example.tradelog.api.controller;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.ShareDataService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.ShareDataModel;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/data", produces = "application/json")
public class ShareDataController {

    private static final Logger log = LoggerFactory.getLogger(ShareDataController.class);

    private ShareDataService shareDataService;

    public ShareDataController(ShareDataService shareDataService) {
        this.shareDataService = shareDataService;
    }

    /**
     * Queries for the share data for given symbol
     * @param symbol - ex 'CVS'
     * @return ShareDataModel
     * @throws TradeLogException -
     */
    @RequestMapping(value = "/share/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ShareDataModel getDataBySymbol(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol) throws TradeLogException {

        if (!RequestValidation.validateGetDataBySymbol(accountId, symbol)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<ShareDataModel> optionalShareDataModel = shareDataService.getShareData(symbol);

        if (optionalShareDataModel.isPresent()) {
            return optionalShareDataModel.get();
        } else {
            log.error("COULD NOT OBTAIN DATA FOR: {}", symbol);
            throw new TradeLogException(ExceptionCode.SHARE_DATA_EMPTY);
        }
    }
}
