package com.ash.vertxspring.verify;

import java.util.ArrayList;
import java.util.List;

public class CounterPartySingletonChecker implements CptyValidator {
    private static CounterPartySingletonChecker INSTANCE;
    private List<CptyEnum> cptyCodes;

    // static initialize this
    {
        cptyCodes = new ArrayList();
        cptyCodes.add(CptyEnum.YODA1);
        cptyCodes.add(CptyEnum.YODA2);
    }

    private CounterPartySingletonChecker() {
    }

    public static CounterPartySingletonChecker getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CounterPartySingletonChecker();
        }

        return INSTANCE;
    }

    // used to represent the list of valid cpty codes
    // we only have one for now i.e USB AG
    private enum CptyEnum {
        YODA1("YODA1"),
        YODA2("YODA2");

        String text="";

        CptyEnum(String enumText) {
            this.text=enumText;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    @Override
    public boolean isValid(String cptyCode) {
        return cptyCodes.contains(CptyEnum.valueOf(cptyCode));
    }

}
