package com.example.gateway.api.gateway;

import com.example.tenant.api.spec.model.TenantModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "tenant-api", url = "localhost:8081/api/v1")
public interface TenantApiProxy {

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.GET)
    ResponseEntity<TenantModel> tenantByUUID(@PathVariable("tenantId") String tenantId);
}
