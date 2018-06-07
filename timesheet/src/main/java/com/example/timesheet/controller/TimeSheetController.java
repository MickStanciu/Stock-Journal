package com.example.timesheet.controller;

import com.example.common.converter.TimeConversion;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.timesheet.exception.ExceptionCode;
import com.example.timesheet.model.TimeSheetEntryModel;
import com.example.timesheet.service.TimeSheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class TimeSheetController {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetController.class);

    private TimeSheetService timeSheetService;

    @Autowired
    public TimeSheetController(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTimeSheetEntries(
            @PathVariable(name = "tenantId") String tenantId,
            @PathVariable(name = "accountId")BigInteger accountId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
            ) {

        if (!RequestValidation.validateGetTimesheet(tenantId, accountId, from, to)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime fromDate;
        LocalDateTime toDate;

        if (from == null || to == null) {
            fromDate = TimeConversion.getStartOfDay();
            toDate = TimeConversion.getEndOfDay();
        } else {
            fromDate = TimeConversion.fromString(from);
            toDate = TimeConversion.fromString(to);
        }

        List<TimeSheetEntryModel> entryList;
        try {
            entryList = timeSheetService.getEntriesByIdAndTime(tenantId, accountId, fromDate, toDate);
        } catch (Exception ex) {
            log.error("", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<ErrorDto> errors = new ArrayList<>();
        if (entryList.isEmpty()) {
            errors.add(new ErrorDto(ExceptionCode.TIMESHEET_NOT_FOUND.name(), ExceptionCode.TIMESHEET_NOT_FOUND.getMessage()));
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<TimeSheetEntryModel>>()
                .withData(entryList)
                .withErrors(errors)
                .build();

        return new ResponseEntity<>(responseEnvelope, HttpStatus.OK);
    }
}
