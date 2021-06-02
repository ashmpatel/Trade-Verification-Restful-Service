package com.ash.vertxspring.verify;

import com.ash.vertxspring.entity.TradeRequest;
import com.ash.vertxspring.exceptions.InvalidTradeMessage;

import java.util.List;

public interface FxOption {
    List<InvalidTradeMessage> isValid();
}
