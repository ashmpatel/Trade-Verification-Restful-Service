package com.ash.vertxspring.factory;

import com.ash.vertxspring.entity.TradeRequest;
import com.ash.vertxspring.exceptions.InvalidTradeMessage;
import com.ash.vertxspring.verify.CounterPartySingletonChecker;
import com.ash.vertxspring.verify.CurrencyValidator;
import com.ash.vertxspring.verify.LegalEntitySingleton;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class BasicTrade implements Trade {
    private String ccyPair;
    private String type;

    private String customer;
    private String legalEntity;
    private String trader;

    private String direction;

    public CurrencyValidator validateCCYCode;

    private List<InvalidTradeMessage> exceptionsWhenProcessing = new LinkedList<>();

    private LegalEntitySingleton legalEntityChecker = LegalEntitySingleton.getInstance();
    private CounterPartySingletonChecker cptyCodeChecker = CounterPartySingletonChecker.getInstance();

    public String getTodaysDate() {
        return TODAYS_DATE;
    }

    public BasicTrade() {
        validateCCYCode = new CurrencyValidator();
    }

    public BasicTrade(String ccyPair, String type, String customer, String legalEntity, String trader, String direction) {
        validateCCYCode = new CurrencyValidator();
        this.ccyPair = ccyPair;
        this.type = type;
        this.customer = customer;
        this.legalEntity = legalEntity;
        this.trader = trader;
        this.direction = direction;
    }

    public List<InvalidTradeMessage> isValid(TradeRequest trade) {

        // check first part of ccy is valid . JSK Currency object is iso 4127 compliant so if
        // it can not decode the ccy pair info then the ccy code is invalid
        try {
            validateCCYCode.getFirstCCY(trade.getCcyPair());
        } catch (InvalidTradeMessage e) {
            exceptionsWhenProcessing.add(e);
        }

        // check second part of ccy pair is valid
        try {
            validateCCYCode.getSecondCCY(trade.getCcyPair());
        } catch (InvalidTradeMessage e) {
            exceptionsWhenProcessing.add(e);
        }

        if (!trade.getType().equals(SPOT) && !trade.getType().equals(FORWARDS) && !trade.getType().equals(VANILLA_OPTION)) {
            exceptionsWhenProcessing.add(new InvalidTradeMessage(String.format(String.format("%s option type is NOT valid", trade.getType()))));
        }

        try {
            // check Customer code is valid
            cptyCodeChecker.isValid(trade.getCustomer());
        } catch (IllegalArgumentException e) {
            exceptionsWhenProcessing.add(invalidCounterparty(trade.getCustomer()));
        }


        try {
            // check LegalEntity e.g USB AG  is valid
            legalEntityChecker.isValid(trade.getLegalEntity());
        } catch (IllegalArgumentException e) {
            exceptionsWhenProcessing.add(invalidLegalEntity(trade.getLegalEntity()));
        }

        // return all the errors
        return exceptionsWhenProcessing;

    }

    // create the exception for an invalid cpty in the Trade Request
    private InvalidTradeMessage invalidCounterparty(String cpty) {
        return new InvalidTradeMessage(String.format(String.format("%s is an invalid counterparty", cpty)));
    }

    // create the exception for an invalid legal entity in the Trade Request
    private InvalidTradeMessage invalidLegalEntity(String legalEntity) {
        return new InvalidTradeMessage(String.format(String.format("%s is an invalid legal entity", legalEntity)));
    }

    // used to represent if we are checking a row or column for valid values
    protected enum OptionType {
        AMERICAN("AMERICAN"),
        EUROPEAN("EUROPEAN");

        String text = "";

        OptionType(String enumText) {
            this.text = enumText;
        }

        @Override
        public String toString() {
            return text;
        }
    }

}
