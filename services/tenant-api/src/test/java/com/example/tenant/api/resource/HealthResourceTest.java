package com.example.tenant.api.resource;

import com.example.tenant.api.service.HealthService;
import com.example.tenant.api.spec.model.HealthModel;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HealthResource.class)
class HealthResourceTest {

    //https://springbootdev.com/2018/02/22/spring-boot-test-writing-unit-tests-for-the-controller-layers-with-webmvctest/

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthService service;


    @Test
    void checkTest() throws Exception {
        HealthModel model = new HealthModel();
        model.setFirstRecordOk(true);
        Mockito.when(service.getHealth()).thenReturn(model);

        mockMvc
                .perform(get("/api/v1/health/check"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstRecordOk", CoreMatchers.is(true)));
    }

    @Test
    void checkPing() throws Exception {
        mockMvc
                .perform(get("/api/v1/health/ping"))
//                .andDo(print())
                .andExpect(status().isOk());
    }

}
