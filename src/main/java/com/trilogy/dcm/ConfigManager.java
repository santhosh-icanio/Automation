/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.trilogy.dcm;

import com.trilogy.dcm.base.Config;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PropertyKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Properties;

@PropertyKey
public class ConfigManager {
    public static final String DEFAULT_ENV = "dev";
    public static final String environment = System.getProperty("environment", "dev");
    @Generated
    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static final String PROPERTIES_PATH = "src/test/resources/config.properties";
    private static final String ENV_PROPERTIES_PATH = "src/test/resources/environment.properties";
    private static Config config = null;
    private static String baseUrl = null;

    public ConfigManager() {
    }

    public static Config getConfig() {
        if (Objects.isNull(config)) {
            config = loadProperties();
        }

        return config;
    }

    public static boolean isMacOs() {
        return DriverManager.OperatingSystem.MAC.name().equals(getConfig().getOperatingSystem());
    }

    public static boolean isPlaywrightAutomation() {
        return DriverManager.AutomationExecutionType.PLAYWRIGHT.name().equals(getConfig().getAutomationType());
    }

    private static Config loadProperties() {
        Config obj = new Config();
        Class<?> clazz = obj.getClass();
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream("src/test/resources/config.properties");

            try {
                properties.load(inputStream);
                Field[] var4 = clazz.getDeclaredFields();
                int var5 = var4.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    Field field = var4[var6];

                    try {
                        if (field.isAnnotationPresent(com.trilogy.dcm.annotations.PropertyKey.class)) {
                            com.trilogy.dcm.annotations.PropertyKey annotation = (com.trilogy.dcm.annotations.PropertyKey) field.getAnnotation(com.trilogy.dcm.annotations.PropertyKey.class);
                            String key = annotation.value();
                            String systemPropertyValue = System.getProperty(key.replaceAll("\\.", "_"));
                            String value = systemPropertyValue != null ? systemPropertyValue : properties.getProperty(key);
                            field.setAccessible(true);
                            if (field.getType().equals(String.class)) {
                                field.set(obj, value);
                            } else if (!field.getType().equals(Boolean.class) && !field.getType().equals(Boolean.TYPE)) {
                                if (!field.getType().equals(Integer.class) && !field.getType().equals(Integer.TYPE)) {
                                    if (!field.getType().equals(Long.class) && !field.getType().equals(Long.TYPE)) {
                                        if (!field.getType().equals(Double.class) && !field.getType().equals(Double.TYPE)) {
                                            if (!field.getType().equals(Float.class) && !field.getType().equals(Float.TYPE)) {
                                                if (!field.getType().equals(Short.class) && !field.getType().equals(Short.TYPE)) {
                                                    field.set(obj, value);
                                                } else {
                                                    field.set(obj, Short.parseShort(value));
                                                }
                                            } else {
                                                field.set(obj, Float.parseFloat(value));
                                            }
                                        } else {
                                            field.set(obj, Double.parseDouble(value));
                                        }
                                    } else {
                                        field.set(obj, Long.parseLong(value));
                                    }
                                } else {
                                    field.set(obj, Integer.parseInt(value));
                                }
                            } else {
                                field.set(obj, Boolean.parseBoolean(value));
                            }
                        }
                    } catch (Exception var13) {
                        Exception e = var13;
                        log.error("Failed to set the value for the field: {}", field.getName(), e);
                    }
                }
            } catch (Throwable var14) {
                try {
                    ((InputStream) inputStream).close();
                } catch (Throwable var12) {
                    var14.addSuppressed(var12);
                }

                throw var14;
            }

            ((InputStream) inputStream).close();
        } catch (IOException var15) {
            IOException e = var15;
            log.error("Failed to load the properties", e);
        }

        return obj;
    }

    public static String getDcmStackBaseUrl() {
        if (baseUrl == null) {
            String key = String.format("com.trilogy.dcm.config.%s-base-url", environment);
            baseUrl = System.getProperty(key.replace("\\.", "_"));
            if (baseUrl == null) {
                Properties envProperties = loadEnvProperties();
                baseUrl = envProperties.getProperty(key);
            }

            if (baseUrl == null) {
                log.error("Base URL not configured for environment: {}", environment);
                throw new IllegalStateException("Base URL not found for environment: " + environment);
            }
        }

        return baseUrl;
    }

    private static Properties loadEnvProperties() {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream("src/test/resources/environment.properties");

            try {
                properties.load(inputStream);
            } catch (Throwable var5) {
                try {
                    ((InputStream) inputStream).close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            ((InputStream) inputStream).close();
        } catch (IOException var6) {
            IOException e = var6;
            log.error("Failed to load env.properties file from path: {}", "src/test/resources/environment.properties", e);
        }

        return properties;
    }
}
