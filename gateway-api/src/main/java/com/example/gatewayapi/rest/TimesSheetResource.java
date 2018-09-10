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

@Path("/v1/timesheet")
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

/*
{
    "data": [
        {
            "id": 7,
            "fromTime": "2018-05-06T22:31:00Z",
            "toTime": "2018-05-06T23:30:00Z",
            "title": "worked on bla 7",
            "state": "FILLED",
            "tenantId": "d79ec11a-2011-4423-ba01-3af8de0a3e10",
            "accountId": 6,
            "project": {
                "id": 1,
                "tenantId": "d79ec11a-2011-4423-ba01-3af8de0a3e10",
                "active": true,
                "title": "Project 1",
                "description": "Project 1 description"
            },
            "task": {
                "id": 2,
                "tenantId": "d79ec11a-2011-4423-ba01-3af8de0a3e10",
                "projectId": 1,
                "active": true,
                "title": "task2",
                "description": "task 2 desc"
            }
        },
        {
            "id": 8,
            "fromTime": "2018-05-07T00:31:00Z",
            "toTime": "2018-05-07T01:30:00Z",
            "title": "worked on bla 8",
            "state": "FILLED",
            "tenantId": "d79ec11a-2011-4423-ba01-3af8de0a3e10",
            "accountId": 6,
            "project": {
                "id": 2,
                "tenantId": "d79ec11a-2011-4423-ba01-3af8de0a3e10",
                "active": true,
                "title": "Project 2",
                "description": "Project 2 description"
            },
            "task": {
                "id": 2,
                "tenantId": "d79ec11a-2011-4423-ba01-3af8de0a3e10",
                "projectId": 2,
                "active": true,
                "title": "task2",
                "description": "task 2 desc"
            }
        }
    ],
    "errors": []
}
 */