/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.DriverManager;
import com.trilogy.dcm.extentreports.ExtentManager;
import com.trilogy.dcm.extentreports.ExtentTestManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private final List<ITestNGMethod> passedTests = new ArrayList<>();
    private final List<ITestNGMethod> failedTests = new ArrayList<>();
    private final List<ITestNGMethod> skippedTests = new ArrayList<>();
    private Instant suiteStartTime;

    private static String getTestMethodName(ITestResult iTestResult) {
        String testName = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(org.testng.annotations.Test.class).testName();
        return testName.isEmpty() ? iTestResult.getMethod().getConstructorOrMethod().getName() : testName;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        try {
            suiteStartTime = Instant.now();
            Files.createDirectories(Paths.get("./Screenshots"));
            Files.createDirectories(Paths.get("./test-output/Reports/Screenshots"));
        } catch (IOException e) {
            log.error("Failed to initialize the Automation", e);
            throw new RuntimeException(e);
        }
        log.info("I am in onStart method {}", iTestContext.getName());
        iTestContext.setAttribute("Contentstack", "Automation testing");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("I am in onFinish method {}", iTestContext.getName());
        ExtentTestManager.endTest();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("{} test is starting.", getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("{} test is succeeded.", getTestMethodName(iTestResult));
        passedTests.add(iTestResult.getMethod());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.info("{} test has failed.", getTestMethodName(iTestResult));
        try {
            Object testInstance = iTestResult.getInstance();
            ExtentTest extentTest = (ExtentTest) iTestResult.getTestClass()
                    .getRealClass()
                    .getMethod("getCurrentTestReportNode")
                    .invoke(testInstance);
            DriverManager driverManager = ExtentTestManager.getTestDriverManager();
            if (extentTest != null && driverManager != null) {
                String screenshotPath = driverManager.captureScreenshot();
                extentTest.log(Status.FAIL, "Test case failed at " + iTestResult.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath.replaceFirst(".", "")).build());
            }
            failedTests.add(iTestResult.getMethod());
        } catch (Exception e) {
            log.error("Failed to log the onTestFailure entry", e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        String testName = getTestMethodName(iTestResult);
        log.info("{} test skipped.", testName);
        ExtentTest parentTest = ExtentTestManager.getTest();
        if (parentTest != null) {
            ExtentTest skippedTest = parentTest.createNode(testName);
            skippedTest.log(Status.SKIP, "Test skipped: " + testName);
        }
        skippedTests.add(iTestResult.getMethod());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        log.info("Test failed but within defined success ratio {}", getTestMethodName(iTestResult));
    }

    @Override
    public void onFinish(ISuite suite) {
        String suiteName = ExtentManager.BASE_SUITE_NAME;
        String dcmStackBaseUrl = ConfigManager.getDcmStackBaseUrl();

        if (Objects.isNull(suiteStartTime))
            suiteStartTime = Instant.now();
        Instant suiteEndTime = Instant.now();

        long totalTimeInSeconds = Duration.between(suiteStartTime, suiteEndTime).getSeconds();

        String formattedTotalTime = String.format("%02d:%02d",
                totalTimeInSeconds / 60, totalTimeInSeconds % 60);  // Format as mm:ss
        int totalPassed = 0;
        int totalFailed = 0;
        int totalSkipped = 0;

        Map<String, ISuiteResult> suiteResults = suite.getResults();
        for (ISuiteResult sr : suiteResults.values()) {
            ITestContext tc = sr.getTestContext();
            totalPassed += tc.getPassedTests().getAllResults().size();
            totalFailed += tc.getFailedTests().getAllResults().size();
            totalSkipped += tc.getSkippedTests().getAllResults().size();
        }

        int totalTestRun = totalPassed + totalFailed + totalSkipped;

        log.info("Environment: {}", ExtentManager.ENVIRONMENT);
        log.info("Application URL: {}", dcmStackBaseUrl);
        log.info("Suite Name: {}", suiteName);
        log.info("Total Test Run: {}", totalTestRun);
        log.info("Total Passed: {}", totalPassed);
        log.info("Total Failed: {}", totalFailed);
        log.info("Total Skipped: {}", totalSkipped);
        log.info("Total Time: {}", formattedTotalTime);
    }


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            if (method.isConfigurationMethod() && !testResult.isSuccess()) {
                ExtentTest extentTest = (ExtentTest) testResult.getTestClass()
                        .getRealClass()
                        .getMethod("getCurrentTestReportNode")
                        .invoke(testResult.getInstance());
                DriverManager driverManager = ExtentTestManager.getTestDriverManager();
                if (extentTest != null && driverManager != null) {
                    if (testResult.getThrowable() != null) {
                        String screenshotPath = driverManager.captureScreenshot();
                        extentTest.log(Status.FAIL, "Test case failed at " + testResult.getThrowable(),
                                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath.replace("/test-output/Reports", "")).build());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to log the configuration method failure in Extent Report.", e);
        }
    }

}