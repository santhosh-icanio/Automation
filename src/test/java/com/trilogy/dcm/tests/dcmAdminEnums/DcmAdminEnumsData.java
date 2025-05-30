/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.dcmAdminEnums;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class DcmAdminEnumsData extends BaseData {

    public static final String LOCATION_NEW_ENUMS_DATA_SHEET = "enums";
    public static final String LOCATOR_EDIT_AND_SEARCH_DATA_SHEET = "searchEdit";

    @org.testng.annotations.DataProvider(name = LOCATION_NEW_ENUMS_DATA_SHEET)
    public static Object[][] getDcmAdminEnumsData() {
        return getTestDataJSON(ConfigManager.getConfig().getDcmAdminEnumsFile(),
                LOCATION_NEW_ENUMS_DATA_SHEET);
    }

    @org.testng.annotations.DataProvider(name = LOCATOR_EDIT_AND_SEARCH_DATA_SHEET)
    public static Object[][] getSearchAndEditEnumsData() {
        return getTestDataJSON(ConfigManager.getConfig().getDcmAdminEnumsFile(),
                LOCATOR_EDIT_AND_SEARCH_DATA_SHEET);
    }
}