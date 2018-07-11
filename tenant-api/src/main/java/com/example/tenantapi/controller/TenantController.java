package com.example.tenantapi.controller;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.tenant.exception.ExceptionCode;
import com.example.tenant.model.TenantModel;
import com.example.tenant.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Path("/api/v1")
@Produces("application/json")
public class TenantController {

    private static final Logger log = LoggerFactory.getLogger(TenantController.class);

    private TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GET
    @Path("/{tenantId}")
    public Response tenantByUUID(@PathParam("tenantId") @DefaultValue("0") String tenantId) {
        //todo: better check, use FieldValidator
        if (tenantId.length() == 0 || tenantId.equals("0")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<ErrorDto> errors = new ArrayList<>();

        //todo: catch all errors
        Optional<TenantModel> tenantModelOptional;
        try {
            tenantModelOptional = tenantService.getTenant(tenantId);
        } catch (Exception ex) {
            log.error("", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }


        if (!tenantModelOptional.isPresent()) {
            errors.add(new ErrorDto(ExceptionCode.TENANT_NOT_FOUND.name(), ExceptionCode.TENANT_NOT_FOUND.getMessage()));
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<TenantModel>()
                .withData(tenantModelOptional.orElse(null))
                .withErrors(errors)
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }
}
