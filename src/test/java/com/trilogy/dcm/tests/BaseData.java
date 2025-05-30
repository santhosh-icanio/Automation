/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests;

import com.trilogy.dcm.data.provider.LocalProvider;
import com.trilogy.dcm.data.reader.DataReader;

public class BaseData {
    protected static Object[][] getTestDataJSON(String filePath, String dataKey) {
        LocalProvider provider = new LocalProvider(filePath, DataReader.InputReaderType.JSON);
        return provider.getInputData(dataKey);
    }
}
