/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests;

import com.aventstack.extentreports.ExtentTest;
import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.DriverManager;
import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.extentreports.ExtentTestManager;
import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.security.SecurityManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.TestException;
import org.testng.annotations.AfterClass;

@Slf4j
public abstract class BaseTest {
    public final ExtentTest etSuite;
    private final WebDriver<?> webDriver;
    @Getter
    private String currentTestName;

    @Getter
    private ExtentTest currentTestReportNode;

    public BaseTest(String testSuiteName) {
        DriverManager manager = new DriverManager();
        this.webDriver = manager.getWebDriver();
        etSuite = ExtentTestManager.startTest(testSuiteName, "", manager);
        this.webDriver.navigateURL(ConfigManager.getDcmStackBaseUrl());
    }

    @AfterClass
    public void tearDown() {
        this.webDriver.tearDown();
    }

    protected WebDriver<?> getWebDriver() {
        return this.webDriver;
    }

    protected void setCurrentTestReportNode(String testName) {
        this.currentTestName = testName;
        this.currentTestReportNode = etSuite.createNode(testName);
    }

    protected void fail(Throwable t) {
        String message = String.format("Given test [%s] failed with exception: [%s]", getCurrentTestName(), t.getMessage());
        getCurrentTestReportNode().fail(message);
        log.error(message, t);
        throw new TestException(t);
    }

    public void fail(String message) {
        getCurrentTestReportNode().fail(message);
        log.error(message);
    }
}