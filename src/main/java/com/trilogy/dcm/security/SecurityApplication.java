/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.security;

import java.util.logging.Logger;

public class SecurityApplication {

    private static final Logger logger = Logger.getLogger(SecurityApplication.class.getName());

    public static void main(String[] args) {
        SecurityManager securityManager = SecurityManager.getInstance();
        logger.info("Program started.");

        securityManager.updateNonEncryptedData();

        logger.info("Program finished.");
    }
}
