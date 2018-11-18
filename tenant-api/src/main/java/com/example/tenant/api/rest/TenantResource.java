package com.example.tenant.api.rest;

import com.example.tenant.api.service.TenantService;
import com.example.tenant.api.spec.exception.ExceptionCode;
import com.example.tenant.api.spec.exception.ResourceErrorException;
import com.example.tenant.api.spec.exception.TenantException;
import com.example.tenant.api.spec.model.TenantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping(value = "/api/v1/", produces = "application/json")
public class TenantResource {

    private static final Logger log = LoggerFactory.getLogger(TenantResource.class);

    private TenantService tenantService;

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.GET)
    public ResponseEntity<TenantModel> tenantByUUID(
            @PathVariable("tenantId") String tenantId
    ) throws TenantException {
        if (tenantId.length() == 0 || tenantId.equals("0")) {
            throw new ResourceErrorException(ExceptionCode.UNKNOWN, "Invalid Request");
        }


        //todo: catch all errors
        Optional<TenantModel> tenantOptional = tenantService.getTenant(tenantId);;

        if (!tenantOptional.isPresent()) {
            throw new TenantException(ExceptionCode.TENANT_NOT_FOUND);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tenantOptional.get());
    }
}
