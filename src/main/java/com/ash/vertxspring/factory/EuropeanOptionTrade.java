package com.ash.vertxspring.factory;

import com.ash.vertxspring.entity.TradeRequest;
import com.ash.vertxspring.exceptions.InvalidTradeMessage;
import com.ash.vertxspring.verify.AmericanOption;
import com.ash.vertxspring.verify.EuropeanOption;
import com.ash.vertxspring.verify.FxOption;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EuropeanOptionTrade extends BasicTrade implements Trade {
    private String excerciseStartDate;
    private String deliveryDate;
    private String expiryDate;
    private String payCcy;
    private BigDecimal premium;
    private String stragegy;
    private String premiumCcy;
    private String premiumType;
    private String premiumDate;
    private String tradeDate;

    private List<InvalidTradeMessage> europeanOptionReadeExceptions = new ArrayList<>();

    public EuropeanOptionTrade(TradeRequest tradeRequest) {
        super(tradeRequest.getCcyPair(), tradeRequest.getType(), tradeRequest.getCustomer(), tradeRequest.getLegalEntity(), tradeRequest.getTrader(), tradeRequest.getDirection());

        this.excerciseStartDate = tradeRequest.getExcerciseStartDate();
        this.deliveryDate = tradeRequest.getDeliveryDate();
        this.expiryDate = tradeRequest.getExpiryDate();
        this.payCcy = tradeRequest.getPayCcy();
        this.premium = tradeRequest.getPremium();
        this.stragegy = tradeRequest.getStrategy();
        this.premiumCcy = tradeRequest.getPremiumCcy();
        this.premiumType = tradeRequest.getPremiumType();
        this.premiumDate = tradeRequest.getPremiumDate();
        this.tradeDate = tradeRequest.getTradeDate();
    }

    @Override
    public List<InvalidTradeMessage> isValid(TradeRequest trade) {

        //do the basic checks all trades needs like ccy pair check
        List<InvalidTradeMessage> basicTradeCheckExceptions = super.isValid(trade);

        // if date rules not met for Option Types
        if (trade.getType() != null) {
            if (trade.getType().equals(VANILLA_OPTION)) {
                if ((trade.getStyle().equals("EUROPEAN") || trade.getStyle().equals("AMERICAN"))) {
                    europeanOptionReadeExceptions.addAll(checkDateRulesForOptins(trade));
                } else {
                    europeanOptionReadeExceptions.add(new InvalidTradeMessage(String.format(String.format("%s option Style is NOT valid", trade.getStyle()))));
                }
            }
        }

        europeanOptionReadeExceptions.addAll(basicTradeCheckExceptions);

        return europeanOptionReadeExceptions;

    }

    private List<InvalidTradeMessage> checkDateRulesForOptins(TradeRequest trade) {
        FxOption optionType = null;
        boolean isValid = true;
        List<InvalidTradeMessage> exceptions = Collections.EMPTY_LIST;

        switch (OptionType.valueOf(trade.getStyle())) {
            case AMERICAN:
                optionType = new AmericanOption(
                        trade.getExpiryDate(),
                        trade.getPremiumDate(),
                        trade.getDeliveryDate(),
                        trade.getExcerciseStartDate(),
                        trade.getTradeDate()
                );
                break;
            case EUROPEAN:
                optionType = new EuropeanOption(trade.getExpiryDate(),
                        trade.getPremiumDate(),
                        trade.getDeliveryDate());
                break;
            default:
                optionType = null;
                break;
        }

        if (optionType != null) {
            exceptions = optionType.isValid();
        }

        return exceptions;
    }

}
