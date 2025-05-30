/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.locationParty;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class LocationPartyData extends BaseData {

    public static final String LOCATION_PARTY_DATA_SHEET = "location";
    public static final String LOCATION_SEARCH_DATA_SHEET = "locationSearch";

    @org.testng.annotations.DataProvider(name = LOCATION_PARTY_DATA_SHEET)
    public static Object[][] getLocationPartyData() {
        return getTestDataJSON(ConfigManager.getConfig().getLocationFile(),
                LOCATION_PARTY_DATA_SHEET);
    }

    @org.testng.annotations.DataProvider(name = LOCATION_SEARCH_DATA_SHEET)
    public static Object[][] getLocationSearchData() {
        return getTestDataJSON(ConfigManager.getConfig().getLocationSearchFile(),
                LOCATION_SEARCH_DATA_SHEET);
    }
}