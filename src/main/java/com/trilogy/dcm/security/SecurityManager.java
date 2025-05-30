/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.security;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.data.reader.DataReader;
import com.trilogy.dcm.exception.DCMException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class SecurityManager {

    private static final SecurityManager SECURITY_MANAGER = new SecurityManager();
    private final DataReader excelReader;
    private final AESEncryptionManager encryptionManager;
    private final List<Object[]> encryptedDataList;

    private SecurityManager() {
        this.excelReader = DataReader.Factory.getInputReader(DataReader.
                InputReaderType.valueOf(ConfigManager.getConfig().getEncryptionDataType()));
        this.encryptionManager = new AESEncryptionManager();
        this.encryptedDataList = readEncryptedDataFromSource();
    }

    public static SecurityManager getInstance() {
        return SECURITY_MANAGER;
    }

    public String getValue(String key) {
        try {
            String encryptedValue = fetchEncryptedDataByKey(key);
            return encryptionManager.decrypt(encryptedValue.replace("encrypt_", ""));
        } catch (Exception e) {
            throw new DCMException("Failed to read the encrypted value", e);
        }
    }

    public void updateNonEncryptedData() {
        log.info("Reading encrypted data from source...");
        List<Object[]> inputData = readEncryptedDataFromSource();

        log.info("Encrypting data...");
        List<Object[]> encryptedData = encryptData(inputData);

        log.info("Updating encrypted data...");
        updateEncryptedData(encryptedData);
    }

    private List<Object[]> readEncryptedDataFromSource() {
        try (InputStream inputStream = getInputStreamFromEncryptionDataFile()) {
            return Arrays.stream(excelReader.getInputData(Objects.requireNonNull(inputStream)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>(0);
        }
    }

    private InputStream getInputStreamFromEncryptionDataFile() {
        try {
            return new FileInputStream(ConfigManager.getConfig().getEncryptionDataPath());
        } catch (FileNotFoundException e) {
            throw new DCMException(e);
        }
    }

    private List<Object[]> encryptData(List<Object[]> inputData) {
        List<Object[]> encryptedData = new ArrayList<>(inputData.size());
        for (Object[] inputDatum : inputData) {
            if (inputDatum.length == 2) {
                Object[] encryptedRow = new Object[inputDatum.length];
                encryptedRow[0] = inputDatum[0];
                String content = (String) inputDatum[1];
                if (content != null && !content.trim().isEmpty()) {
                    encryptedRow[1] = !content.startsWith("encrypt_") ? String.format("encrypt_%s", encryptionManager.encrypt(content)) : content;
                    encryptedData.add(encryptedRow);
                }

            } else {
                String message = "Invalid input data length: " + Arrays.toString(inputDatum);
                log.warn(message);
                throw new IllegalArgumentException(message);
            }
        }
        return encryptedData;
    }

    private String fetchEncryptedDataByKey(String key) {
        return encryptedDataList.stream()
                .filter((a) -> a[0].equals(key))
                .findFirst()
                .map((a) -> String.valueOf(a[1]))
                .orElse("");
    }

    private void updateEncryptedData(List<Object[]> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Default");

        createHeaderRow(sheet);
        populateDataRows(sheet, data);

        try (FileOutputStream outputStream = new FileOutputStream(ConfigManager.getConfig().getEncryptionDataPath())) {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("Failed to write the Excel output", e);
        } finally {
            closeWorkbook(workbook);
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Key");
        headerRow.createCell(1).setCellValue("Value");
    }

    private void populateDataRows(Sheet sheet, List<Object[]> data) {
        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : rowData) {
                Cell cell = row.createCell(colNum++);
                setCellValue(cell, field);
            }
        }
    }

    private void setCellValue(Cell cell, Object field) {
        if (field instanceof String) {
            cell.setCellValue((String) field);
        } else if (field instanceof Integer) {
            cell.setCellValue((Integer) field);
        } else if (field instanceof Double) {
            cell.setCellValue((Double) field);
        }
    }

    private void closeWorkbook(Workbook workbook) {
        try {
            workbook.close();
        } catch (IOException e) {
            log.error("Failed to close the Excel output", e);
        }
    }
}
