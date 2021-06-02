package com.ash.vertxspring.factory;

import com.ash.vertxspring.entity.TradeRequest;

public interface AbstractTradeFactory<T> {
    T create(TradeRequest tradeRequest) ;
}
