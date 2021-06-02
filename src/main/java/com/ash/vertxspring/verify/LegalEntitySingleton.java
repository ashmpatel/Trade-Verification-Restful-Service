package com.ash.vertxspring.verify;

import java.util.ArrayList;
import java.util.List;

public class LegalEntitySingleton implements CptyValidator  {

        private static LegalEntitySingleton INSTANCE;
        private List<String> legalEntities;

        // static initialize this
        {
            legalEntities = new ArrayList();
            legalEntities.add("USB AG");
        }

    private LegalEntitySingleton() {
    }

    public static LegalEntitySingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new LegalEntitySingleton();
        }

        return INSTANCE;
    }


    @Override
    public boolean isValid(String cptyCode) {
        return legalEntities.contains(cptyCode);
    }
}
