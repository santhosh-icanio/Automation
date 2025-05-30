package com.trilogy.dcm.tests.contractKit;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class ContractKitData extends BaseData {
    public static final String CONTRACTKIT_DATA_SHEET = "contractKit";
    public static final String CONTRACTKITSEARCH_DATA_SHEET = "search";

    @org.testng.annotations.DataProvider(name = CONTRACTKIT_DATA_SHEET)
    public static Object[][] getContractKitData() {
        return getTestDataJSON(ConfigManager.getConfig().getContractKitFile(),
                CONTRACTKIT_DATA_SHEET);
    }

    @org.testng.annotations.DataProvider(name = CONTRACTKITSEARCH_DATA_SHEET)
    public static Object[][] getContractKitSearchData() {
        return getTestDataJSON(ConfigManager.getConfig().getContractKitFile(),
                CONTRACTKITSEARCH_DATA_SHEET);
    }


}
