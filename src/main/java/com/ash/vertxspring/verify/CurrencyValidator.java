package com.ash.vertxspring.verify;

import com.ash.vertxspring.exceptions.InvalidCCYPair;
import org.springframework.stereotype.Component;

@Component
public class CurrencyValidator {

    public boolean verifyisISOCcy(String ccyCode) {
        try {
            java.util.Currency.getInstance(ccyCode);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;

    }

    public void getFirstCCY(String ccyPair) throws InvalidCCYPair {
        if (!verifyisISOCcy(ccyPair.substring(0, 3))) {
            throw new InvalidCCYPair("The currency pair is invalid : " + ccyPair);
        }
    }

    public void getSecondCCY(String ccyPair) throws InvalidCCYPair {
        if (!verifyisISOCcy(ccyPair.substring(3))) {

            throw new InvalidCCYPair("The currency pair is not a valid ccy: " + ccyPair);

        }
    }

}
