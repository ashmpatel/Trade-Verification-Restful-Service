package com.ash.vertxspring.service;

import com.ash.vertxspring.entity.TradeRequest;
import com.ash.vertxspring.exceptions.InvalidTradeMessage;
import com.ash.vertxspring.factory.AbstractTradeFactory;
import com.ash.vertxspring.factory.Trade;
import com.ash.vertxspring.factory.TradeFactoryprovider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeVerificationService {

    public List<InvalidTradeMessage> verifyTrade(TradeRequest trade) {

        // we are only processing FX trades and Options for now
        AbstractTradeFactory<Trade> factoryProvider = TradeFactoryprovider.getFactory("FX");
        List<InvalidTradeMessage> errors = new ArrayList<>();

        // get the abstract factory to create the right trade object from the trade data
        Trade tradeCreated = factoryProvider.create(trade);
        if (tradeCreated != null) {
            // if we can create a valid trade, verify the data
            errors = tradeCreated.isValid(trade);
        } else {
            errors.add(new InvalidTradeMessage("Invalid Trade Request"));
        }

        // return any errros we got when verifying the trade data
        return errors;
    }

}
