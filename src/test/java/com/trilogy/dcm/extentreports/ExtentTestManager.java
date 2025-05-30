/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.trilogy.dcm.DriverManager;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    private static final Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static final Map<Integer, DriverManager> driverManagerMap = new HashMap<>();
    private static final ExtentReports extent = ExtentManager.createExtentReports();

    public static synchronized ExtentTest getTest() {
        int id = (int) Thread.currentThread().getId();
        ExtentTest extentTest = extentTestMap.get(id);
        DriverManager driverManager = driverManagerMap.get(id);
        if (extentTest == null) {
            extentTest = ExtentTestManager.startTest(" ", " ", driverManager);
        }
        return extentTest;
    }

    public static synchronized DriverManager getTestDriverManager() {
        int id = (int) Thread.currentThread().getId();
        return driverManagerMap.get(id);
    }

    public static synchronized ExtentTest startTest(String testName, String desc, DriverManager driverManager) {
        ExtentTest extentTest = extent.createTest(testName, desc);

        int id = (int) Thread.currentThread().getId();
        extentTestMap.put(id, extentTest);
        driverManagerMap.put(id, driverManager);
        return extentTest;
    }

    public static synchronized void endTest() {
        extent.flush();
    }
}