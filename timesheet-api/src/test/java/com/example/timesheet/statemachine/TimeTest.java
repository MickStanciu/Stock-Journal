package com.example.timesheet.statemachine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.AssertJUnit.assertEquals;

public class TimeTest {

    @Test
    public void testSerialization() throws JsonProcessingException {
        LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String result = mapper.writeValueAsString(date);
        assertEquals("\"2014-12-20T02:30:00\"", result);
    }
}
