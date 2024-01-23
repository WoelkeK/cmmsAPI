package pl.medos.cmmsApi.util.imports;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Logger;

public class DateConverter {

    private static final Logger LOGGER = Logger.getLogger(DateConverter.class.getName());
    static LocalDate convertDate(String date) {
        LOGGER.info("DataXLS_String " + date);
        LOGGER.info("Start parsing string to date " + date.toString());

        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        LocalDate localDate = LocalDate.parse(date, inputDateFormatter);
        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String outputDateString = localDate.format(outputDateFormatter);
        LOGGER.info("ConvertedDate() " + localDate.toString());
        return localDate;
    }

        static LocalDateTime convertDateTime(String date) {
        LOGGER.info("DataXLS_String " + date);
        LOGGER.info("Start parsing string to date " + date.toString());

        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        LocalDateTime localDate = LocalDateTime.parse(date, inputDateFormatter);
        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String outputDateString = localDate.format(outputDateFormatter);
        LOGGER.info("ConvertedDate() " + localDate.toString());
        return localDate;
    }
}
