package xyz.cryptohows.backend.round.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter {

    public static final int DATE_ONLY_UP_TO_MONTH = 7;
    public static final String FIRST_DAY_OF_THE_MONTH = "-01";
    public static final DateTimeFormatter PERIOD_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private LocalDateConverter() {
    }

    public static LocalDate formatDate(String date) {
        if (date.contains("-")) {
            return formatDateByDash(date);
        }
        return formatDateByPeriod(date);
    }

    private static LocalDate formatDateByDash(String date) {
        if (date.length() <= DATE_ONLY_UP_TO_MONTH) {
            return LocalDate.parse(date + FIRST_DAY_OF_THE_MONTH, DateTimeFormatter.ISO_DATE);
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

    private static LocalDate formatDateByPeriod(String date) {
        String[] split = date.split("\\.");
        String month = split[1];
        if (month.length() < 2) {
            month = "0" + month;
        }

        String day = split[2];
        if (day.length() < 2) {
            day = "0" + day;
        }

        date = split[0] + month + day;
        return LocalDate.parse(date, PERIOD_FORMATTER);
    }
}
