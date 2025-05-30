/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.data.provider;


import com.trilogy.dcm.data.reader.DataReader;
import com.trilogy.dcm.data.reader.ExcelReader;
import com.trilogy.dcm.data.reader.JSONReader;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Objects;

public abstract class DataProvider {

    private final InputStream inputStream;
    private final DataReader.InputReaderType type;

    protected DataProvider(String fileName, DataReader.InputReaderType type) {
        this.type = type;
        this.inputStream = readFile(fileName);
    }

    protected abstract InputStream readFile(String fileName);

    public Object[][] getInputData(@Nullable String sheetName) {
        DataReader reader = getInputReader(sheetName);
        return reader.getInputData(this.inputStream);
    }

    public Object[][] getInputData() {
        DataReader reader = getInputReader(null);
        return reader.getInputData(this.inputStream);
    }

    private DataReader getInputReader(String sheetName) {
        if (Objects.requireNonNull(type) == DataReader.InputReaderType.EXCEL) {
            return new ExcelReader(sheetName);
        }
        if (Objects.requireNonNull(type) == DataReader.InputReaderType.JSON) {
            return new JSONReader(sheetName);
        }
        return new ExcelReader(sheetName);
    }

}
