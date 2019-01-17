package com.example.tenant.api.resource;

import com.example.tenant.api.service.TenantService;
import com.example.tenant.api.spec.model.TenantModel;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TenantResource.class)
class TenantResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TenantService service;


    @Test
    void checkTest() throws Exception {
        String uuid = "123-123-123";
        TenantModel model = new TenantModel(uuid, "NAME");

        Mockito.when(service.getTenant(anyString())).thenReturn(Optional.of(model));

        mockMvc
                .perform(get("/api/v1/{tenantId}", "123-123-123"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(uuid)));
    }

    @Test
    void checkTestNotFound() throws Exception {
        String uuid = "123-123-123";
        Mockito.when(service.getTenant(anyString())).thenReturn(Optional.empty());

        mockMvc
                .perform(get("/api/v1/{tenantId}", "123-123-123"))
//                .andDo(print())
                .andExpect(status().is(404));
    }
}
