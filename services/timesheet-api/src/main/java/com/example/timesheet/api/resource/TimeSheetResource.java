package com.example.timesheet.api.resource;

import com.example.common.converter.TimeConversion;
import com.example.timesheet.api.exception.ExceptionCode;
import com.example.timesheet.api.exception.TimeSheetException;
import com.example.timesheet.api.model.TimeSheetEntryModel;
import com.example.timesheet.api.service.TimeSheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class TimeSheetResource {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetResource.class);

    private TimeSheetService timeSheetService;

    public TimeSheetResource(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<List<TimeSheetEntryModel>> getTimeSheetEntries(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("accountId") long accountId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
            ) throws TimeSheetException {

        if (!RequestValidation.validateGetTimesheet(tenantId, accountId, from, to)) {
            throw new TimeSheetException(ExceptionCode.BAD_REQUEST);
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

        List<TimeSheetEntryModel> entryList = timeSheetService.getEntriesByIdAndTime(tenantId, accountId, fromDate, toDate);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entryList);
    }
}
