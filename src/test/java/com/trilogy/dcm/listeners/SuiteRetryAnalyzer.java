/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.listeners;

import com.aventstack.extentreports.Status;
import com.trilogy.dcm.extentreports.ExtentTestManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class SuiteRetryAnalyzer implements ISuiteListener, ITestListener, IInvokedMethodListener {
    private static int currentRetryCount = 0;
    private static Set<String> failedTests = new HashSet<>();
    private static boolean suiteHasFailures = false;
    private static boolean setupFailed = false;

    public static int getCurrentRetryCount() {
        return currentRetryCount;
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        if (method.isConfigurationMethod() && testResult.getStatus() == ITestResult.FAILURE) {
            log.error("Setup method failed: {} - Marking suite for retry", method.getTestMethod().getMethodName());
            suiteHasFailures = true;
            setupFailed = true;
        }
    }

    @Override
    public void onStart(ISuite suite) {
        log.info("Starting suite: {} - Attempt: {}", suite.getName(), currentRetryCount + 1);
        if (currentRetryCount == 0) {
            // Only clear state on first run
            failedTests.clear();
            suiteHasFailures = false;
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        final int MAX_SUITE_RETRY_ATTEMPTS = 1; // Set a retry limit
        if ((suiteHasFailures || setupFailed) && currentRetryCount < MAX_SUITE_RETRY_ATTEMPTS) {
            currentRetryCount++;
            log.info("Suite {} has failures. Retrying entire suite. Attempt: {}", suite.getName(), currentRetryCount);

            try {
                TestNG testng = new TestNG();
                List<String> suiteFiles = new ArrayList<>();
                String suiteXmlPath = suite.getXmlSuite().getFileName();

                if (suiteXmlPath == null || suiteXmlPath.isEmpty()) {
                    log.error("Could not determine suite XML file path. Skipping retry.");
                    return;
                }

                suiteFiles.add(suiteXmlPath);
                testng.setTestSuites(suiteFiles);
                testng.setUseDefaultListeners(false);
                testng.setPreserveOrder(true);
                testng.setVerbose(2);

                testng.run(); // Running the suite again

            } catch (Exception e) {
                log.error("Failed to retry suite: {}", e.getMessage(), e);
            }
        } else {
            log.info("Suite {} has failures but max retry count ({}) reached.", suite.getName(), MAX_SUITE_RETRY_ATTEMPTS);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Not needed for suite retry
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        failedTests.remove(testName);
        log.info("Test passed: {}", testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        failedTests.add(testName);
        suiteHasFailures = true;
        log.error("Test failed: {} - Will retry entire suite", testName);
        ExtentTestManager.getTest().log(Status.FAIL,
                String.format("Test failed: %s. Suite retry will be triggered.", testName));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        if (failedTests.contains(testName)) {
            log.info("Previously failed test skipped: {}", testName);
        }
    }
} 