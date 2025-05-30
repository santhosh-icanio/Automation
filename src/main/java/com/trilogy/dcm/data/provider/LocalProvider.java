/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.data.provider;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.data.reader.DataReader;
import com.trilogy.dcm.exception.DCMException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LocalProvider extends DataProvider {

    public LocalProvider(String fileName, DataReader.InputReaderType type) {
        super(fileName, type);
    }

    @Override
    public InputStream readFile(String fileName) {
        try {
            return new FileInputStream(ConfigManager.getConfig().getDataFolder() + fileName);
        } catch (FileNotFoundException e) {
            throw new DCMException(e);
        }
    }
}
;