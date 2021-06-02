package com.ash.vertxspring.verify;

import com.ash.vertxspring.exceptions.InvalidTradeMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EuropeanOption implements FxOption {

    // check the dates for FX, Options, FWDS
    protected FxDateRulesChecker dateRulesChecker = FxDateRulesChecker.getInstance();

    protected LocalDate expiryDate;
    protected LocalDate premiumDate;
    protected LocalDate deliveryDate;

    List<InvalidTradeMessage> europeanOptionsExceptions = new ArrayList<>();


    public EuropeanOption(String expiryDate, String premiumDate, String deliveryDate) {
        this.expiryDate = SingleTonDateFormatChecker.getInstance().convertDateToRightFormat(expiryDate);
        this.premiumDate = SingleTonDateFormatChecker.getInstance().convertDateToRightFormat(premiumDate);
        this.deliveryDate = SingleTonDateFormatChecker.getInstance().convertDateToRightFormat(deliveryDate);
    }

    // check the logic for the American date option
    private boolean checkDEuropeanOptionDates() {
        return dateRulesChecker.firstDateIsBeforeSecondDate(expiryDate, deliveryDate) &&
                dateRulesChecker.firstDateIsBeforeSecondDate(premiumDate, deliveryDate);
    }

    // check if the dates are set correctly for American Options otherwise return exception
    public List<InvalidTradeMessage> isValid() {

        if (!checkDEuropeanOptionDates()) {
            europeanOptionsExceptions.add(new InvalidTradeMessage("OptionType does not meet the date validation criteria"));
        }

        return europeanOptionsExceptions;
    }

}
