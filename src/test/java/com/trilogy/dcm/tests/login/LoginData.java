/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.login;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class LoginData extends BaseData {

    public static final String LOGIN_DATA = "login";
    public static final String INVALID_USER_NAME = "invalidUserName";
    public static final String INVALID_PASSWORD = "invalidPassword";

    @org.testng.annotations.DataProvider(name = LOGIN_DATA)
    public static Object[][] getLoginData() {
        return getTestDataJSON(ConfigManager.getConfig().getLoginFile(),
                LOGIN_DATA);
    }

    @org.testng.annotations.DataProvider(name = INVALID_USER_NAME)
    public static Object[][] getInvalidUserName() {
        return getTestDataJSON(ConfigManager.getConfig().getLoginFile(),
                INVALID_USER_NAME);
    }

    @org.testng.annotations.DataProvider(name = INVALID_PASSWORD)
    public static Object[][] getInvalidPassword() {
        return getTestDataJSON(ConfigManager.getConfig().getLoginFile(),
                INVALID_PASSWORD);
    }
}