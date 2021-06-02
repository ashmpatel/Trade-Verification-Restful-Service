package com.ash.vertxspring.factory;

import com.ash.vertxspring.entity.TradeRequest;
import com.ash.vertxspring.exceptions.InvalidTradeMessage;

import java.util.List;

public interface Trade {
    String SPOT = "Spot";
    String FORWARDS = "Forwards";
    String VANILLA_OPTION = "VanillaOption";
    String TODAYS_DATE = "2020-10-09";

    List<InvalidTradeMessage> isValid(TradeRequest trade);
}
