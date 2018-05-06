package com.example.core.conversion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeConversion {

    public static LocalDateTime fromString(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
        return LocalDateTime.parse(inputDate + " 00:00:00", formatter);
    }

    public static String now() {
        return LocalDate.now(ZoneOffset.UTC).toString();
    }
}
