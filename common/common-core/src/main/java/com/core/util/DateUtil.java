package com.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

/**
 * @author yuangy
 */
public class DateUtil {

    private static final String EMPTY = "";
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DEFAULT_DATE_TIME_NEATLY_TO_SECOND_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter DEFAULT_DATE_TIME_NEATLY_TO_MILLI_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    /*
     * ================================== 此时 ========================================
     */

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static LocalTime nowTime() {
        return LocalTime.now();
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

    /*
     * ================================== 加减 ========================================
     */

    public static class SECOND {
        public static LocalTime plus(LocalTime localTime, long value) {
            return Optional.ofNullable(localTime).map((item) -> item.plusSeconds(value)).orElse(localTime);
        }

        public static LocalDateTime plus(LocalDateTime localDateTime, long value) {
            return Optional.ofNullable(localDateTime).map((item) -> item.plusSeconds(value)).orElse(localDateTime);
        }
    }

    public static class MINUTE {
        public static LocalTime plus(LocalTime localTime, long value) {
            return Optional.ofNullable(localTime).map((item) -> item.plusMinutes(value)).orElse(localTime);
        }

        public static LocalDateTime plus(LocalDateTime localDateTime, long value) {
            return Optional.ofNullable(localDateTime).map((item) -> item.plusMinutes(value)).orElse(localDateTime);
        }
    }

    public static class HOUR {
        public static LocalTime plus(LocalTime localTime, long value) {
            return Optional.ofNullable(localTime).map((item) -> item.plusHours(value)).orElse(localTime);
        }

        public static LocalDateTime plus(LocalDateTime localDateTime, long value) {
            return Optional.ofNullable(localDateTime).map((item) -> item.plusHours(value)).orElse(localDateTime);
        }
    }

    public static class DAY {
        public static LocalDate plus(LocalDate localDate, long value) {
            return Optional.ofNullable(localDate).map((item) -> item.plusDays(value)).orElse(localDate);
        }

        public static LocalDateTime plus(LocalDateTime localDateTime, long value) {
            return Optional.ofNullable(localDateTime).map((item) -> item.plusDays(value)).orElse(localDateTime);
        }
    }

    public static class MONTH {
        public static LocalDate plus(LocalDate localDate, long value) {
            return Optional.ofNullable(localDate).map((item) -> item.plusMonths(value)).orElse(localDate);
        }

        public static LocalDateTime plus(LocalDateTime localDateTime, long value) {
            return Optional.ofNullable(localDateTime).map((item) -> item.plusMonths(value)).orElse(localDateTime);
        }
    }

    public static class YEAR {
        public static LocalDate plus(LocalDate localDate, long value) {
            return Optional.ofNullable(localDate).map((item) -> item.plusYears(value)).orElse(localDate);
        }

        public static LocalDateTime plus(LocalDateTime localDateTime, long value) {
            return Optional.ofNullable(localDateTime).map((item) -> item.plusYears(value)).orElse(localDateTime);
        }
    }

    public static class WEEK {
        public static LocalDate plus(LocalDate localDate, long value) {
            return Optional.ofNullable(localDate).map((item) -> item.plusWeeks(value)).orElse(localDate);
        }

        public static LocalDateTime plus(LocalDateTime localDateTime, long value) {
            return Optional.ofNullable(localDateTime).map((item) -> item.plusWeeks(value)).orElse(localDateTime);
        }
    }

    /*
     * ================================== 特定时间 ========================================
     */

    public static LocalDate getMonthBegin(LocalDate localDate, int floatMonth) {
        return Optional.ofNullable(localDate).map((item) -> MONTH.plus(localDate, floatMonth).withDayOfMonth(1)).orElse(localDate);
    }

    public static LocalDateTime getMonthBegin(LocalDateTime localDateTime, int floatMonth) {
        return Optional.ofNullable(localDateTime).map((item) -> MONTH.plus(localDateTime, floatMonth).withDayOfMonth(1).withSecond(0).withMinute(0).withHour(0)).orElse(localDateTime);
    }

    public static LocalDate getMonthEnd(LocalDate localDate, int floatMonth) {
        return Optional.ofNullable(localDate).map((item) -> MONTH.plus(localDate, floatMonth).with(TemporalAdjusters.lastDayOfMonth())).orElse(localDate);
    }

    public static LocalDateTime getMonthEnd(LocalDateTime localDateTime, int floatMonth) {
        return Optional.ofNullable(localDateTime).map((item) -> MONTH.plus(localDateTime, floatMonth).with(TemporalAdjusters.lastDayOfMonth()).withSecond(59).withMinute(59).withHour(23)).orElse(localDateTime);
    }

    public static LocalDateTime getDayBegin(LocalDateTime localDateTime, int floatMonth) {
        return Optional.ofNullable(localDateTime).map((item) -> DAY.plus(localDateTime, floatMonth).withSecond(0).withMinute(0).withHour(0)).orElse(localDateTime);
    }

    public static LocalDateTime getDayEnd(LocalDateTime localDateTime, int floatMonth) {
        return Optional.ofNullable(localDateTime).map((item) -> DAY.plus(localDateTime, floatMonth).withSecond(59).withMinute(59).withHour(23)).orElse(localDateTime);
    }

    /*
     * ================================== 格式化 ========================================
     */

    public static String formatDefault(LocalDate localDate) {
        return Optional.ofNullable(localDate).map((item) -> item.format(DEFAULT_DATE_FORMATTER)).orElse(EMPTY);
    }

    public static String formatDefault(LocalTime localTime) {
        return Optional.ofNullable(localTime).map((item) -> item.format(DEFAULT_TIME_FORMATTER)).orElse(EMPTY);
    }

    public static String formatDefault(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map((item) -> item.format(DEFAULT_DATE_TIME_FORMATTER)).orElse(EMPTY);
    }

    public static String format(String pattern, LocalDate localDate) {
        return Optional.ofNullable(localDate).map((item) -> item.format(DateTimeFormatter.ofPattern(pattern))).orElse(EMPTY);
    }

    public static String format(String pattern, LocalTime localTime) {
        return Optional.ofNullable(localTime).map((item) -> item.format(DateTimeFormatter.ofPattern(pattern))).orElse(EMPTY);
    }

    public static String format(String pattern, LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map((item) -> item.format(DateTimeFormatter.ofPattern(pattern))).orElse(EMPTY);
    }

    /*
     * ================================== 转化 ========================================
     */

    public static LocalDate parseDate(String pattern, String source) {
        try {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate parseDateDefault(String source) {
        try {
            return LocalDate.parse(source, DEFAULT_DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalTime parseTime(String pattern, String source) {
        try {
            return LocalTime.parse(source, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalTime parseTimeDefault(String source) {
        try {
            return LocalTime.parse(source, DEFAULT_DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime parseDateTime(String pattern, String source) {
        try {
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime parseDateTimeDefault(String source) {
        try {
            return LocalDateTime.parse(source, DEFAULT_DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * ================================== 时间戳 ========================================
     */

    public static String timestampNeatlyToMilli() {
        return timestampNeatlyToMilli(nowDateTime());
    }

    public static String timestampNeatlyToMilli(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map((item) -> item.format(DEFAULT_DATE_TIME_NEATLY_TO_MILLI_FORMATTER)).orElse(EMPTY);
    }

    public static String timestampNeatlyToSecond() {
        return timestampNeatlyToSecond(nowDateTime());
    }

    public static String timestampNeatlyToSecond(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map((item) -> item.format(DEFAULT_DATE_TIME_NEATLY_TO_SECOND_FORMATTER)).orElse(EMPTY);
    }

    public static String timestampString() {
        return String.valueOf(timestamp(nowDateTime()));
    }

    public static long timestamp() {
        return timestamp(nowDateTime());
    }

    public static long timestamp(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map((item) -> item.toInstant(ZoneOffset.of("+8")).toEpochMilli()).orElse(0L);
    }

    public static long seconds() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

}
