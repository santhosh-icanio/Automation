/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.exception;

public class DCMException extends RuntimeException {
    public DCMException(String message) {
        super(message);
    }

    public DCMException(String message, Throwable cause) {
        super(message, cause);
    }

    public DCMException(Throwable cause) {
        super(cause);
    }
}
