package com.example.common.converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeConversion {

    public static LocalDateTime fromString(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
        return LocalDateTime.parse(inputDate + " 00:00:00", formatter);
    }

    public static LocalDateTime getStartOfDay() {
        return LocalDateTime
                .now(ZoneOffset.UTC)
                .truncatedTo(ChronoUnit.DAYS);
    }

    public static LocalDateTime getEndOfDay() {
        return LocalDateTime
                .now(ZoneOffset.UTC)
                .withHour(23)
                .withMinute(59)
                .truncatedTo(ChronoUnit.MINUTES);
    }
}
