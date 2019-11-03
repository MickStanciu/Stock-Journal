package com.example.common.converter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class TimeConverter {

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

    public static LocalDateTime fromInstant(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }


    public static Timestamp fromOffsetDateTime(OffsetDateTime offsetDateTime) {
        return Timestamp.from(offsetDateTime.toInstant());
    }

    public static OffsetDateTime fromTimestamp(Timestamp timestamp) {
        //TODO: not sure about ZoneId
        return OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp.getTime()), ZoneId.systemDefault());
    }

    public static Function<String, OffsetDateTime> toOffsetDateTime = string -> {
        //TODO: NOT IMPLEMENTED
        System.out.println("> " + string);
        return OffsetDateTime.now();
    };
}
