package com.example.core.rest;

import com.example.common.converter.TimeConversion;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.core.exception.ExceptionCode;
import com.example.core.model.TimeSheetEntryModel;
import com.example.core.service.TimesheetService;
import com.example.core.validation.RequestValidation;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/")
@Produces("application/json")
public class TimesheetRest implements TimesheetRestInterface {

    private static final Logger log = Logger.getLogger(TimesheetRest.class);

    @Inject
    private TimesheetService timesheetService;

    @GET
    @Path("/{tenantId}/{accountId}")
    public Response getTimesheetEntries(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId,
            @QueryParam("from") String from,
            @QueryParam("to") String to
    ) {
        if (!RequestValidation.validateGetTimesheet(tenantId, accountId, from, to)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
            //todo: validate from to
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
            entryList = timesheetService.getEntriesByIdAndTime(tenantId, accountId, fromDate, toDate);
        } catch (Exception ex) {
            log.error(ex);
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
