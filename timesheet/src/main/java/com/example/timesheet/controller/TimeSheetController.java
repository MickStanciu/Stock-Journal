//package com.example.timesheet.controller;
//
//import com.example.common.converter.TimeConversion;
//import com.example.common.rest.dto.ErrorDto;
//import com.example.common.rest.envelope.ResponseEnvelope;
//import com.example.timesheet.exception.ExceptionCode;
//import com.example.timesheet.model.TimeSheetEntryModel;
//import com.example.timesheet.service.TimeSheetService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.ws.rs.DefaultValue;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Response;
//import java.math.BigInteger;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Path("/api/v1")
//@Produces("application/json")
//public class TimeSheetController {
//
//    private static final Logger log = LoggerFactory.getLogger(TimeSheetController.class);
//
//    private TimeSheetService timeSheetService;
//
//    @Autowired
//    public TimeSheetController(TimeSheetService timeSheetService) {
//        this.timeSheetService = timeSheetService;
//    }
//
//    @GET
//    @Path("/{tenantId}/{accountId}")
//    public Response getTimeSheetEntries(
//            @PathParam("tenantId") @DefaultValue("0") String tenantId,
//            @PathParam("accountId") BigInteger accountId,
//            @QueryParam("from") String from,
//            @QueryParam("to") String to
//            ) {
//
//        if (!RequestValidation.validateGetTimesheet(tenantId, accountId, from, to)) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//
//        LocalDateTime fromDate;
//        LocalDateTime toDate;
//
//        if (from == null || to == null) {
//            fromDate = TimeConversion.getStartOfDay();
//            toDate = TimeConversion.getEndOfDay();
//        } else {
//            fromDate = TimeConversion.fromString(from);
//            toDate = TimeConversion.fromString(to);
//        }
//
//        List<TimeSheetEntryModel> entryList;
//        try {
//            entryList = timeSheetService.getEntriesByIdAndTime(tenantId, accountId, fromDate, toDate);
//        } catch (Exception ex) {
//            log.error("", ex);
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }
//
//        List<ErrorDto> errors = new ArrayList<>();
//        if (entryList.isEmpty()) {
//            errors.add(new ErrorDto(ExceptionCode.TIMESHEET_NOT_FOUND.name(), ExceptionCode.TIMESHEET_NOT_FOUND.getMessage()));
//        }
//
//        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<TimeSheetEntryModel>>()
//                .withData(entryList)
//                .withErrors(errors)
//                .build();
//
//        return Response.status(Response.Status.OK)
//                .entity(responseEnvelope)
//                .build();
//    }
//}
