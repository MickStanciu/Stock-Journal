package com.example.common.converter

import java.sql.Timestamp
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class TimeConverter {

    companion object {
        fun fromString(inputDate: String): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)
            return LocalDateTime.parse("$inputDate 00:00:00", formatter)
        }

        fun fromUSDateString(inputDate: String): OffsetDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return OffsetDateTime.of(LocalDateTime.parse("$inputDate 00:00:00", formatter), ZoneOffset.UTC)
        }

        fun getStartOfDay(): LocalDateTime {
            return LocalDateTime
                    .now(ZoneOffset.UTC)
                    .truncatedTo(ChronoUnit.DAYS)
        }

        fun getEndOfDay(): LocalDateTime {
            return LocalDateTime
                    .now(ZoneOffset.UTC)
                    .withHour(23)
                    .withMinute(59)
                    .truncatedTo(ChronoUnit.MINUTES)
        }

        fun fromInstant(instant: Instant?): LocalDateTime {
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        }


        fun fromOffsetDateTime(offsetDateTime: OffsetDateTime): Timestamp {
            return Timestamp.from(offsetDateTime.toInstant())
        }

        fun getTimestampNow(): Timestamp {
            return Timestamp(Date().time)
        }

        fun fromTimestamp(timestamp: Timestamp): OffsetDateTime {
            return OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(timestamp.time), ZoneId.of("UTC"))
        }

        fun nextYear(): OffsetDateTime {
            return OffsetDateTime.now(ZoneId.of("UTC")).plusYears(1)
        }

        fun getOffsetDateTimeNow(): OffsetDateTime {
            return OffsetDateTime.now()
        }

        var toOffsetDateTime = { text: String -> OffsetDateTime.parse(text) }
    }

}