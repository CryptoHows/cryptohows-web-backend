package xyz.cryptohows.backend.round.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter {

    public static final int DATE_ONLY_UP_TO_MONTH = 7;
    public static final String FIRST_DAY_OF_THE_MONTH = "-01";

    private LocalDateConverter() {
    }

    public static LocalDate formatDate(String date) {
        if (date.length() <= DATE_ONLY_UP_TO_MONTH) {
            return LocalDate.parse(date + FIRST_DAY_OF_THE_MONTH, DateTimeFormatter.ISO_DATE);
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
}
