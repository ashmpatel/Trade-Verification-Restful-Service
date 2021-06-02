package com.ash.vertxspring.factory;

// return the right factory type for the trades we process
public class TradeFactoryprovider {

    public static AbstractTradeFactory getFactory(String choice) {

        if (Instruments.valueOf(choice) == Instruments.FX) {
            return new TradeFactory();
        } else return null;
    }

// for now , we are only processing a few types of trades. I will call them FX trades and Options
    public enum Instruments {
        FX("FX");

        String text = "";

        Instruments(String enumText) {
            this.text = enumText;
        }

        @Override
        public String toString() {
            return text;
        }
    }

}
