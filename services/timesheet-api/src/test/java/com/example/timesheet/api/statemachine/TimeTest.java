package com.example.timesheet.api.statemachine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;


class TimeTest {

    @Test
    void testLocalDateTimeSerialization() throws JsonProcessingException {
        LocalDateTime date = LocalDateTime.of(2014, 12, 20, 2, 30);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String result = mapper.writeValueAsString(date);
        Assertions.assertEquals("\"2014-12-20T02:30:00\"", result);
    }

    @Test
    void testInstantSerialization() throws JsonProcessingException {
        Instant instant = Instant.parse("2018-05-06T22:31:00Z");
        System.out.println(instant);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String result = mapper.writeValueAsString(instant);
        Assertions.assertEquals("\"2018-05-06T22:31:00Z\"", result);
    }
}
