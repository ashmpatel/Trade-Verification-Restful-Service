package com.ash.vertxspring.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SingleTonDateFormatChecker implements DateValidator {

    private static SingleTonDateFormatChecker INSTANCE;
    private static String dateFormat = "yyyy-MM-dd";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

    // static initialize this
    {
        formatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    private SingleTonDateFormatChecker() {
    }

    public static SingleTonDateFormatChecker getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SingleTonDateFormatChecker();
        }

        return INSTANCE;
    }

    @Override
    public boolean isValid(String dateStr) {
        try {
            formatter.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalDate convertDateToRightFormat(String dateValue) {
        return  LocalDate.parse(dateValue, formatter);
    }
}
