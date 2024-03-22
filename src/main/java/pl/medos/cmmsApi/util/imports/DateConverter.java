package pl.medos.cmmsApi.util.imports;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Logger;

@Slf4j
public class DateConverter {

    public static LocalDate convertDate(String date) {
        log.debug("DataXLS_String " + date);
        log.debug("Start parsing string to date " + date.toString());

        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        LocalDate localDate = LocalDate.parse(date, inputDateFormatter);
        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String outputDateString = localDate.format(outputDateFormatter);
        log.debug("ConvertedDate() " + localDate.toString());
        return localDate;
    }

    public static LocalDateTime convertDateTime(String date) {
        log.debug("DataXLS_String " + date);
        log.debug("Start parsing string to date " + date.toString());

        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        LocalDateTime localDate = LocalDateTime.parse(date, inputDateFormatter);
        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String outputDateString = localDate.format(outputDateFormatter);
        log.debug("ConvertedDate() " + localDate.toString());
        return localDate;
    }
}
