/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.pages.login;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class LoginPage extends BasePage {
    private static final String LOCATOR_EMAIL = "[name='LOGINNAME']";
    private static final String LOCATOR_PASSWORD = "[name='PASSWORD']";
    private static final String LOCATOR_LOGIN_BUTTON = "button.btn.btn-lg.btn-green";
    private static final String LOCATOR_INVALID_LOGIN_TEXT = "[class='text-danger']";
    private static final String LOCATOR_PARTY_BUTTON = "ul.navbar-nav.top-nav li a#Party span";


    public static final String EXPECTED_LOGIN_ERROR = " Unable to login. Authentication failed because the supplied login name and/or password are invalid. ";
    public static final String EXPECTED_DCM_PRODUCT_PAGE_TEXT = "Party";
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";


    public LoginPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public boolean isPartyMenuVisible() {
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        return isElementPresent(LOCATOR_PARTY_BUTTON);
    }

    public void clickLogin() {
        clickElement(LOCATOR_LOGIN_BUTTON, "Login");
    }

    public void setPassword(String password) {
        setTextElement(LOCATOR_PASSWORD, password, "PASSWORD");
    }

    public void setEmail(String email) {
        setTextElement(LOCATOR_EMAIL, email, "LOGINNAME");
    }

    public String retrieveLoginError() {
        return getText(LOCATOR_INVALID_LOGIN_TEXT, "Login Error");
    }

    public String retrieveSuccessfulLoginText() {
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        return getText(LOCATOR_PARTY_BUTTON, "Party Button");
    }
}