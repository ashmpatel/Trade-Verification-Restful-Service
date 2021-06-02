package com.ash.vertxspring.factory;

import com.ash.vertxspring.entity.TradeRequest;
import com.ash.vertxspring.exceptions.InvalidTradeMessage;
import com.ash.vertxspring.verify.DateCheckHelper;
import com.ash.vertxspring.verify.FxDateRulesChecker;
import com.ash.vertxspring.verify.SingleTonDateFormatChecker;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpotForwardTrade extends BasicTrade implements Trade {

    // used for checking dates
    private SingleTonDateFormatChecker dateValidator = SingleTonDateFormatChecker.getInstance();
    // check the dates for FX, Options, FWDS
    private FxDateRulesChecker dateRulesChecker = FxDateRulesChecker.getInstance();

    private String tradeDate;
    private BigDecimal amount1;
    private BigDecimal amount2;
    private Double rate;
    private String valueDate;
    private List<InvalidTradeMessage> spotTradeExceptions = new ArrayList<>();

    public SpotForwardTrade(TradeRequest tradeRequest) {
        super(tradeRequest.getCcyPair(), tradeRequest.getType(), tradeRequest.getCustomer(), tradeRequest.getLegalEntity(), tradeRequest.getTrader(), tradeRequest.getDirection());

        this.tradeDate = tradeRequest.getTradeDate();
        this.amount1 = tradeRequest.getAmount1();
        this.amount2 = tradeRequest.getAmount2();
        this.rate = tradeRequest.getRate();
        this.valueDate = tradeRequest.getValueDate();
    }


    @Override
    public List<InvalidTradeMessage> isValid(TradeRequest trade) {

        //do the basic checks all trades needs like ccy pair check
        List<InvalidTradeMessage> basicTradeCheckExceptions = super.isValid(trade);

        // check trade date is valid
        if (!dateValidator.isValid(trade.getTradeDate())) {
            spotTradeExceptions.add(invalidDate(DateEnum.TradeDate));
        }

        // check tradedate is todays date
        if (dateRulesChecker.firstDateEqualToOrAfterSecondDate(dateValidator.convertDateToRightFormat(trade.getTradeDate()),
                dateValidator.convertDateToRightFormat(getTodaysDate()))) {
            spotTradeExceptions.add(invalidTradeDateBeforeToday(DateEnum.TradeDate));
        }

        dateRulesChecker.firstDateAfterSecondDate(dateValidator.convertDateToRightFormat(trade.getTradeDate()),
                dateValidator.convertDateToRightFormat(trade.getValueDate()));

        if (trade.getType().equals(SPOT)) {
            if (!dateValidator.convertDateToRightFormat(trade.getTradeDate()).plusDays(2).equals(dateValidator.convertDateToRightFormat(trade.getValueDate()))) {
                spotTradeExceptions.add((new InvalidTradeMessage(String.format("ValueDate is not set correctly for SPOT trade"))));
            }
            ;
        }

        InvalidTradeMessage valueDateTradeDateError = valueDateCheck(trade.getTradeDate(), trade.getValueDate());
        if (valueDateTradeDateError != null) {
            spotTradeExceptions.add(valueDateTradeDateError);
        }

        //check valuedate is valid
        if (!dateValidator.isValid(trade.getValueDate())) {
            spotTradeExceptions.add(invalidDate(DateEnum.ValueDate));
        }

        // collect all exceptions
        spotTradeExceptions.addAll(basicTradeCheckExceptions);

        return spotTradeExceptions;
    }

    // checks for the date rules for value dates and tradedates
    private InvalidTradeMessage valueDateCheck(String tradeDate, String valueDate) {
        InvalidTradeMessage errorMessage = null;

        // generic date checks
        // check tradeDate is BEFORE valuedate.
        // spec says value date can not be before trade date FOR all types of trades
        LocalDate valueDateConverted = dateValidator.convertDateToRightFormat(valueDate);

        boolean valueDateBeforeTradeDate = dateRulesChecker.firstDateIsBeforeSecondDate(valueDateConverted, dateValidator.convertDateToRightFormat(tradeDate));

        boolean dateFallsOnWeekend = DateCheckHelper.dateFallsOnWeekend(valueDateConverted);

        if (valueDateBeforeTradeDate)
            errorMessage = new InvalidTradeMessage(String.format(String.format("%s valuedate is before tradedate", valueDate)));

        if (dateFallsOnWeekend)
            errorMessage = new InvalidTradeMessage(String.format(String.format("%s falls on a weekend", valueDate)));

        return errorMessage;
    }


    // create the exception for an invalid date in the Trade Request
    private InvalidTradeMessage invalidDate(DateEnum dateField) {
        return new InvalidTradeMessage(String.format(String.format("%s is invalid", dateField)));
    }

    // used to represent if we are checking a row or column for valid values
    private enum DateEnum {
        TradeDate("Trade Date"),
        ValueDate("Value Date");

        String text = "";

        DateEnum(String enumText) {
            this.text = enumText;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    // create the exception for an invalid date in the Trade Request
    private InvalidTradeMessage invalidTradeDateBeforeToday(DateEnum dateField) {
        return new InvalidTradeMessage(String.format(String.format("%s is BEFORE todays date", dateField)));
    }


}
