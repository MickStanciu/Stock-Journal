package com.example.core.rest;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.core.exception.ExceptionCode;
import com.example.core.model.TimesheetEntry;
import com.example.core.service.TimesheetService;
import com.example.core.validation.RequestValidation;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/")
@Produces("application/json")
public class TimesheetRest {

    private static final Logger log = Logger.getLogger(TimesheetRest.class);

    @Inject
    private TimesheetService timesheetService;

    @GET
    @Path("/{tenantId}/{accountId}")
    public Response getAccount(
            @PathParam("tenantId") @DefaultValue("0") String tenantId,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId,
            @QueryParam("from") @DefaultValue("") String from,
            @QueryParam("to") @DefaultValue("") String to
    ) {
        if (!RequestValidation.validateGetTimesheet(tenantId, accountId, from, to)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
            //todo: validate from to
        }

        List<TimesheetEntry> entryList;
        try {
            entryList = timesheetService.getEntriesByIdAndTime(tenantId, accountId, null, null);
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        List<ErrorDto> errors = new ArrayList<>();
        if (entryList.isEmpty()) {
            errors.add(new ErrorDto(ExceptionCode.TIMESHEET_NOT_FOUND.name(), ExceptionCode.TIMESHEET_NOT_FOUND.getMessage()));
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<TimesheetEntry>>()
                .withData(entryList)
                .withErrors(errors)
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }
}
