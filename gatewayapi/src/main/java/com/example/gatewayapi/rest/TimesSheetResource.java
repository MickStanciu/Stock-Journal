package com.example.gatewayapi.rest;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.service.TimeSheetService;
import com.example.timesheet.model.TimeSheetEntryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Path("/v2/timesheet")
@Produces(MediaType.APPLICATION_JSON)
public class TimesSheetResource  extends AbstractResource {

    private static final Logger log = LoggerFactory.getLogger(TimesSheetResource.class);

    @Inject
    private TimeSheetService timesheetService;

    @GET
    @Path("/{accountId}")
    public Response getTimeSheetDetails(
            @HeaderParam("authkey") String token,
            @PathParam("accountId") BigInteger accountId,
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

        return Response
                .status(responseStatus)
                .entity(responseEnvelope)
                .build();
    }
}
