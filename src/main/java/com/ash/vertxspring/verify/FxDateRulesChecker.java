package com.ash.vertxspring.verify;

import java.time.LocalDate;

public class FxDateRulesChecker implements RulesValidator {

    private static FxDateRulesChecker INSTANCE;

    private FxDateRulesChecker() {
    }

    public static FxDateRulesChecker getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FxDateRulesChecker();
        }

        return INSTANCE;
    }

    @Override
    public boolean firstDateIsBeforeSecondDate(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isBefore(secondDate);
    }

    @Override
    public boolean firstDateAfterSecondDate(LocalDate firstDate, LocalDate secondDate) {
        return secondDate.isBefore(secondDate);
    }

    @Override
    public boolean firstDateEqualToOrAfterSecondDate(LocalDate firstDate, LocalDate secondDate) {
        return secondDate.isBefore(secondDate);
    }

}