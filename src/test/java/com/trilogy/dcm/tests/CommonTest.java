/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests;

import org.testng.Assert;

import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.security.SecurityManager;

public class CommonTest {

    public static void login(Object[][] loginData, LoginPage loginPage) {
        loginPage.setEmail(loginData[0][0].toString());
        loginPage.setPassword(SecurityManager.getInstance().getValue(loginData[0][1].toString()));
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isPartyMenuVisible(), "Login was not successful");
    }
}
