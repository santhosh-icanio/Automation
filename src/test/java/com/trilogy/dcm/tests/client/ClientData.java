package com.trilogy.dcm.tests.client;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class ClientData extends BaseData {
    public static final String CLIENT_DATA_SHEET = "client";
    public static final String CLIENTSEARCH_DATA_SHEET = "Search";
    public static final String CLIENTEDIT_DATA_SHEET = "edit";


    @org.testng.annotations.DataProvider(name = CLIENT_DATA_SHEET)
    public static Object[][] getClientData() {
        return getTestDataJSON(ConfigManager.getConfig().getClientFile(),
                CLIENT_DATA_SHEET);
    }
    @org.testng.annotations.DataProvider(name = CLIENTSEARCH_DATA_SHEET)
    public static Object[][] getClientSearchData() {
        return getTestDataJSON(ConfigManager.getConfig().getClientFile(),
                CLIENTSEARCH_DATA_SHEET);
    }
    @org.testng.annotations.DataProvider(name = CLIENTEDIT_DATA_SHEET)
    public static Object[][] getClientEditData() {
        return getTestDataJSON(ConfigManager.getConfig().getClientFile(),
                CLIENTEDIT_DATA_SHEET);
    }

}

