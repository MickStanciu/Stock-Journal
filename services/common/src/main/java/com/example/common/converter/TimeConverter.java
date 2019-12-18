package com.example.common.converter;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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

    public static Timestamp getTimestampNow() {
        return new Timestamp(new Date().getTime());
    }

    public static OffsetDateTime fromTimestamp(Timestamp timestamp) {
        return OffsetDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp.getTime()), ZoneId.of("UTC"));
    }

    public static OffsetDateTime nextYear() {
        return OffsetDateTime.now(ZoneId.of("UTC")).plusYears(1);
    }

    public static OffsetDateTime getOffsetDateTimeNow() {
        return OffsetDateTime.now();
    }

    public static Function<String, OffsetDateTime> toOffsetDateTime = OffsetDateTime::parse;
}
