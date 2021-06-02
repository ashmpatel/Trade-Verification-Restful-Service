package com.ash.vertxspring.verify;


import java.time.LocalDate;

public interface RulesValidator {

   boolean firstDateIsBeforeSecondDate(LocalDate firstDate, LocalDate secondDate);

   boolean firstDateAfterSecondDate(LocalDate firstDate, LocalDate secondDate);

   boolean firstDateEqualToOrAfterSecondDate(LocalDate firstDate, LocalDate secondDate);

}