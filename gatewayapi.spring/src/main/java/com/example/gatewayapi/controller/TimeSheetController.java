package com.example.gatewayapi.controller;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.core.model.TimeSheetEntryModel;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.service.TimesheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/timesheet", produces = MediaType.APPLICATION_JSON_VALUE)

public class TimeSheetController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetController.class);

    private TimesheetService timesheetService;

    @Autowired
    public TimeSheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity getAccountDetails(
            @RequestHeader("authkey") String token,
            @PathVariable("accountId") BigInteger accountId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
    ) {
        //todo validate input
        List<ErrorDto> errors = new ArrayList<>();
        HttpStatus responseStatus = HttpStatus.OK;

        List<TimeSheetEntryModel> response = null;
        try {
            String tenantId = getTenantId(token);
            response = timesheetService.getTimesheetEntries(tenantId, accountId, from, to);
        } catch (GatewayApiException e) {
            log.error(e.getMessage());
            errors.add(new ErrorDto(ExceptionCode.TIMESHEET_EMPTY.name(), ExceptionCode.TIMESHEET_EMPTY.getMessage()));
            responseStatus = HttpStatus.NOT_FOUND;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<TimeSheetEntryModel>>()
                .withData(response)
                .withErrors(errors)
                .build();

        return ResponseEntity
                .status(responseStatus)
                .body(responseEnvelope);
    }

}
