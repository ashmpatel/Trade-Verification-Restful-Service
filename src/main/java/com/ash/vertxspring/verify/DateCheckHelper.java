package com.ash.vertxspring.verify;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateCheckHelper {

    public static boolean dateFallsOnWeekend(LocalDate dateValue) {

        boolean result = false;

        Calendar c1 = Calendar.getInstance();
        c1.setTime(Date.from(dateValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        if ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
            //sat or sunday
            result = true;
        }
        return result;
    }

}
