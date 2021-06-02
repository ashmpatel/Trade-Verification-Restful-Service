package com.ash.vertxspring.entity;

public class TradeVerificationResponse {
    TradeRequest trade;
    String errorMessages;

    public TradeVerificationResponse(TradeRequest trade, String errorMessages) {
        this.trade = trade;
        this.errorMessages = errorMessages;
    }

    public TradeRequest getTrade() {
        return trade;
    }

    public void setTrade(TradeRequest trade) {
        this.trade = trade;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "TradeVerificationResponse{" +
                "trade=" + trade +
                ", errorMessages='" + errorMessages + '\'' +
                '}';
    }
}
