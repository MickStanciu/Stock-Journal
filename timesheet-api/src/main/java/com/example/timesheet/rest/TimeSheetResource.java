package com.example.timesheet.rest;

import com.example.common.converter.TimeConversion;
import com.example.timesheet.api.spec.model.TimeSheetEntryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class TimeSheetResource {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetResource.class);

    @Autowired
    private TimeSheetService timeSheetService;

    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<TimeSheetEntryModel> getTimeSheetEntries(
            @PathVariable("tenantId") @DefaultValue("0") String tenantId,
            @PathVariable("accountId") long accountId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
            ) {

        if (!RequestValidation.validateGetTimesheet(tenantId, accountId, from, to)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        List<ErrorDto> errors = new ArrayList<>();
        if (entryList.isEmpty()) {
            errors.add(new ErrorDto(ExceptionCode.TIMESHEET_NOT_FOUND.name(), ExceptionCode.TIMESHEET_NOT_FOUND.getMessage()));
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<TimeSheetEntryModel>>()
                .withData(entryList)
                .withErrors(errors)
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }
}
