/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.base.playwright.PlaywrightWebDriver;
import com.trilogy.dcm.exception.DCMException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Getter
@Slf4j
public class DriverManager {

    private final WebDriver<?> webDriver;

    public DriverManager() {
        webDriver = PlaywrightWebDriver.init();
    }

    public String captureScreenshot() {
        try {
            return captureScreenshotForPlaywright();
        } catch (Exception e) {
            log.error("Failed to capture screenshot", e);
            throw new DCMException(e);
        }
    }

    private String captureScreenshotForPlaywright() {
        String screenshotFileName = Instant.now().toString().replace(":", "_").replace("T", "_").replace("Z", "") + ".png";
        Path screenshotPath = Paths.get("./test-output/Reports/Screenshots", screenshotFileName);
        getWebDriver().captureScreenShot(screenshotPath);
        return screenshotPath.toString();
    }

    public enum AutomationExecutionType {
        PLAYWRIGHT
    }

    public enum OperatingSystem {
        MAC, WINDOWS, LINUX
    }
}