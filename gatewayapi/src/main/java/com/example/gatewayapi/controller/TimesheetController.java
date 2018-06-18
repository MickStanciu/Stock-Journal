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
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/api/v1/timesheet")
@Produces("application/json")
public class TimesheetController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(TimesheetController.class);

    private TimesheetService timesheetService;

    @Autowired
    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @GET
    @Path("/{accountId}")
    public Response getAccountDetails(
            @HeaderParam("authkey") String token,
            @PathParam("accountId") @DefaultValue("0") BigInteger accountId,
            @QueryParam("from") String from,
            @QueryParam("to") String to
    ) {
        //todo validate input
        List<ErrorDto> errors = new ArrayList<>();
        Response.Status responseStatus = Response.Status.OK;

        List<TimeSheetEntryModel> response = null;
        try {
            String tenantId = getTenantId(token);
            response = timesheetService.getTimesheetEntries(tenantId, accountId, from, to);
        } catch (GatewayApiException e) {
            log.error(e.getMessage());
            errors.add(new ErrorDto(ExceptionCode.TIMESHEET_EMPTY.name(), ExceptionCode.TIMESHEET_EMPTY.getMessage()));
            responseStatus = Response.Status.NOT_FOUND;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<List<TimeSheetEntryModel>>()
                .withData(response)
                .withErrors(errors)
                .build();

        return Response.status(responseStatus)
                .entity(responseEnvelope)
                .build();
    }

}
