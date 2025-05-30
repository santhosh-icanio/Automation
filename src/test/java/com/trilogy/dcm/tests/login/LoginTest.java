/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.login;

import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.security.SecurityManager;
import com.trilogy.dcm.tests.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

@Listeners({TestListenerAdapter.class})
@Slf4j
public class LoginTest extends BaseTest {

    private final LoginPage loginPage;

    public LoginTest() {
        super("Login");
        loginPage = new LoginPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setCurrentTestReportNode(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }


    @Test(testName = "Login with invalid user name", dataProvider = LoginData.INVALID_USER_NAME, dataProviderClass = LoginData.class, priority = 1)
    public void testLoginWithInValidUsername(String invalidEmail, String password) {
        try {
            loginPage.setEmail(invalidEmail);
            loginPage.setPassword(SecurityManager.getInstance().getValue(password));
            loginPage.clickLogin();
            String actualUserNameErrorMessage = loginPage.retrieveLoginError();
            Assert.assertEquals(actualUserNameErrorMessage, LoginPage.EXPECTED_LOGIN_ERROR, "Username error message is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "Login with invalid password", dataProvider = LoginData.INVALID_PASSWORD, dataProviderClass = LoginData.class, priority = 2)
    public void testLoginWithInValidPassword(String email, String invalidPassword) {
        try {
            loginPage.setEmail(email);
            loginPage.setPassword(invalidPassword);
            loginPage.clickLogin();
            String actualUserNameErrorMessage = loginPage.retrieveLoginError();
            Assert.assertEquals(actualUserNameErrorMessage, LoginPage.EXPECTED_LOGIN_ERROR, "Password error message is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "Successful Login", dataProvider = LoginData.LOGIN_DATA, dataProviderClass = LoginData.class, priority = 3)
    public void testSuccessfulLogin(String email, String password) {
        try {
            loginPage.setEmail(email);
            loginPage.setPassword(SecurityManager.getInstance().getValue(password));
            loginPage.clickLogin();
            String actualSuccessMessage = loginPage.retrieveSuccessfulLoginText();
            Assert.assertEquals(actualSuccessMessage, LoginPage.EXPECTED_DCM_PRODUCT_PAGE_TEXT, "Welcome text is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }
}