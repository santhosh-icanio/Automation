/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.base;

import com.trilogy.dcm.annotations.PropertyKey;
import lombok.Data;

@Data
public class Config {

    private static final String ENV_PREFIX = "com.trilogy.dcm.config.";

    @PropertyKey(ENV_PREFIX + "os.name")
    private String operatingSystem;

    @PropertyKey(ENV_PREFIX + "automation-tool")
    private String automationType;

    @PropertyKey(ENV_PREFIX + "playwright.binary")
    private String playwrightBinary;

    @PropertyKey(ENV_PREFIX + "playwright.channel")
    private String playwrightChannel;

    @PropertyKey(ENV_PREFIX + "playwright.isHeadless")
    private String playwrightIsHeadless;

    @PropertyKey(ENV_PREFIX + "browser")
    private String browser;

    @PropertyKey(ENV_PREFIX + "input-file")
    private String inputFile;

    @PropertyKey(ENV_PREFIX + "default-timeout")
    private String defaultTimeOut;

    @PropertyKey(ENV_PREFIX + "timeout")
    private String timeOut;

    @PropertyKey(ENV_PREFIX + "default-explicit-polling-interval")
    private String defaultExplicitPollingInterval;

    @PropertyKey(ENV_PREFIX + "spark-report-path")
    private String sparkReportPath;

    @PropertyKey(ENV_PREFIX + "extent-config")
    private String extentConfig;

    @PropertyKey(ENV_PREFIX + "maximum-stage-limit")
    private String maximumStageLimit;

    @PropertyKey(ENV_PREFIX + "max-element-attempts")
    private String maxElementAttempts;

    @PropertyKey(ENV_PREFIX + "login-json-file")
    private String loginFile;

    @PropertyKey(ENV_PREFIX + "person-json-file")
    private String personFile;

    @PropertyKey(ENV_PREFIX + "contractKit-json-file")
    private String contractKitFile;

    @PropertyKey(ENV_PREFIX + "components-json-file")
    private String componentsFile;

    @PropertyKey(ENV_PREFIX + "client-json-file")
    private String clientFile;

    @PropertyKey(ENV_PREFIX + "course-json-file")
    private String courseFile;

    @PropertyKey(ENV_PREFIX + "low_level-timeout")
    private String lowLevelTimeout;

    @PropertyKey(ENV_PREFIX + "mid_level-timeout")
    private String midLevelTimeout;

    @PropertyKey(ENV_PREFIX + "high_level-timeout")
    private String highLevelTimeout;

    @PropertyKey(ENV_PREFIX + "encryption.secret-key")
    private String encryptionSecretKey;

    @PropertyKey(ENV_PREFIX + "encryption.data-path")
    private String encryptionDataPath;

    @PropertyKey(ENV_PREFIX + "encryption.data-type")
    private String encryptionDataType;

    @PropertyKey(ENV_PREFIX + "entries-compare-version-file")
    private String compareVersionData;

    @PropertyKey(ENV_PREFIX + "invisibility-timeout")
    private String invisibilityTimeOut;

    @PropertyKey(ENV_PREFIX + "data-folder")
    private String dataFolder;

    @PropertyKey(ENV_PREFIX + "report-title")
    private String reportTitle;

    @PropertyKey(ENV_PREFIX + "location-json-file")
    private String locationFile;

    @PropertyKey(ENV_PREFIX + "locationSearch-json-file")
    private String locationSearchFile;

    @PropertyKey(ENV_PREFIX + "newProduct-json-file")
    private String newProductSearchFile;

    @PropertyKey(ENV_PREFIX + "newProduct-json-file")
    private String productSearchFile;

    @PropertyKey(ENV_PREFIX + "dcmAdminEnums-json-file")
    private String dcmAdminEnumsFile;


}