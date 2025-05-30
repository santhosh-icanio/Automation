package com.trilogy.dcm.tests.components;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class ComponentData extends BaseData {
    public static final String CREATECOMPONENT_DATA_SHEET = "createComponent";
    public static final String SEARCHCOMPONENT_DATA_SHEET = "searchComponent";
    public static final String EDITCOMPONENT_DATA_SHEET = "editComponent";


    @org.testng.annotations.DataProvider(name = CREATECOMPONENT_DATA_SHEET)
    public static Object[][] getCreateComponentData() {
        return getTestDataJSON(ConfigManager.getConfig().getComponentsFile(),
                CREATECOMPONENT_DATA_SHEET);
    }
    @org.testng.annotations.DataProvider(name = SEARCHCOMPONENT_DATA_SHEET)
    public static Object[][] getSearchComponent() {
        return getTestDataJSON(ConfigManager.getConfig().getComponentsFile(),
                SEARCHCOMPONENT_DATA_SHEET);
    }
    @org.testng.annotations.DataProvider(name = EDITCOMPONENT_DATA_SHEET)
    public static Object[][] getEditComponent() {
        return getTestDataJSON(ConfigManager.getConfig().getComponentsFile(),
                EDITCOMPONENT_DATA_SHEET);
    }


}
