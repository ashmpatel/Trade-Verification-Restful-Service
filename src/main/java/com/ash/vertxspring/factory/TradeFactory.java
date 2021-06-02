package com.ash.vertxspring.factory;

import com.ash.vertxspring.entity.TradeRequest;

// create the correct type of Object based on the trade data
public class TradeFactory implements AbstractTradeFactory<Trade> {

    private static final String SPOT = "Spot";
    private static final String FORWARDS = "Forwards";
    private static final String EUROPEAN = "EUROPEAN";
    private static final String AMERICAN = "AMERICAN";
    private static final String VANILLAOPTION = "VanillaOption";

    @Override
    public Trade create(TradeRequest tradeRequest) {

        if (SPOT.equals(tradeRequest.getType()) || FORWARDS.equals(tradeRequest.getType())) {
            return new SpotForwardTrade(tradeRequest);
        } else if (VANILLAOPTION.equals(tradeRequest.getType())) {
            if (EUROPEAN.equals(tradeRequest.getStyle())) {
                return new EuropeanOptionTrade(tradeRequest);
            } else if (AMERICAN.equals(tradeRequest.getStyle())) {
                return new EuropeanOptionTrade(tradeRequest);
            }
        }

        return null;
    }
}
