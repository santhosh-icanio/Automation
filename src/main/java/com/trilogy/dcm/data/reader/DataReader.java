/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.data.reader;

import java.io.InputStream;

public interface DataReader {
    Object[][] getInputData(InputStream inputStream);


    enum InputReaderType {
        EXCEL, JSON, CSV
    }

    class Factory {

        public static DataReader getInputReader(InputReaderType type) {
            return new ExcelReader();

        }


    }
}
