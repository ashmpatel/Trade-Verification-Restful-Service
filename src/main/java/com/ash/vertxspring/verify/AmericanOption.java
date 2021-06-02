package com.ash.vertxspring.verify;

import com.ash.vertxspring.exceptions.InvalidTradeMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AmericanOption extends EuropeanOption implements FxOption {

    private LocalDate exerciseStartDate = null;

    private LocalDate tradeDate = null;

    //do the basic checks all trades needs like ccy pair check
    private List<InvalidTradeMessage> americalOptionsExceptions = new ArrayList<>();

    public AmericanOption(String expiryDate, String premiumDate, String deliveryDate, String exerciseStartDate, String tradeDate) {
        super(expiryDate, premiumDate, deliveryDate);
        this.exerciseStartDate = SingleTonDateFormatChecker.getInstance().convertDateToRightFormat(exerciseStartDate);
        this.tradeDate = SingleTonDateFormatChecker.getInstance().convertDateToRightFormat(tradeDate);
    }

    private boolean checkDates() {
        return dateRulesChecker.firstDateAfterSecondDate(exerciseStartDate, tradeDate) &&
                dateRulesChecker.firstDateIsBeforeSecondDate(exerciseStartDate, expiryDate);
    }

    @Override
    public List<InvalidTradeMessage> isValid() {
        //do the basic checks all trades needs like ccy pair check
        List<InvalidTradeMessage> basicTradeCheckExceptions = super.isValid();

        if (!checkDates()) {
            americalOptionsExceptions.add(new InvalidTradeMessage("OptionType does not meet the date validation criteria"));
        }
        americalOptionsExceptions.addAll(basicTradeCheckExceptions);
        return americalOptionsExceptions;
    }
}
