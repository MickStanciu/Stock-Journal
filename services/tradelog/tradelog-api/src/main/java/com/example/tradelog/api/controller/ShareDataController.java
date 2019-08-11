package com.example.tradelog.api.controller;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.ShareDataService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.ShareDataModel;
import com.example.tradelog.api.validator.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * Sets data for given symbol
     * @param accountId -
     * @param symbol -
     * @param model -
     * @return ShareDataModel
     * @throws TradeLogException
     */
    @RequestMapping(value = "/share/{symbol}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ShareDataModel setDataForSymbol(
            @RequestHeader("accountId") String accountId,
            @PathVariable("symbol") String symbol,
            @RequestBody ShareDataModel model
            ) throws TradeLogException {

        if (!RequestValidation.validateSetDataBySymbol(accountId, symbol, model)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        Optional<ShareDataModel> optionalShareDataModel = shareDataService.setShareData(symbol, model);

        if (optionalShareDataModel.isPresent()) {
            return optionalShareDataModel.get();
        } else {
            log.error("COULD NOT SET DATA FOR: {}", symbol);
            throw new TradeLogException(ExceptionCode.SHARE_DATA_EMPTY);
        }
    }
}
