/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.personParty;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class PersonPartyData extends BaseData {

    public static final String PERSONPARTY_DATA_SHEET = "person";
    public static final String SEARCHPERSONPARTY_DATA_SHEET = "search";
    public static final String EDITPERSONPARTY_DATA_SHEET = "edit";

    @org.testng.annotations.DataProvider(name = PERSONPARTY_DATA_SHEET)
    public static Object[][] getPersonPartyData() {
        return getTestDataJSON(ConfigManager.getConfig().getPersonFile(),
                PERSONPARTY_DATA_SHEET);
    }
    @org.testng.annotations.DataProvider(name = SEARCHPERSONPARTY_DATA_SHEET)
    public static Object[][] getSearchPersonPartyData() {
        return getTestDataJSON(ConfigManager.getConfig().getPersonFile(),
                SEARCHPERSONPARTY_DATA_SHEET);
    }
    @org.testng.annotations.DataProvider(name = EDITPERSONPARTY_DATA_SHEET)
    public static Object[][] getEditPersonPartyData() {
        return getTestDataJSON(ConfigManager.getConfig().getPersonFile(),
                EDITPERSONPARTY_DATA_SHEET);
    }
}
