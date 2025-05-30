/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.data.reader;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ExcelReader implements DataReader {

    private final String sheetName;

    public ExcelReader() {
        this.sheetName = null;
    }

    public ExcelReader(String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    public Object[][] getInputData(InputStream inputStream) {
        try {
            XSSFSheet sheet = getSheet(inputStream);
            int rowCount = getRowCount(sheet);
            int cellCount = getCellCount(sheet);
            String[][] contentTypeData = new String[rowCount][cellCount];
            for (int i = 1; i <= rowCount; i++) {
                for (int j = 0; j < cellCount; j++) {
                    String content = getCellData(sheet, i, j);
                    if (!Objects.isNull(content))
                        contentTypeData[i - 1][j] = content;
                }
            }
            return contentTypeData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCellData(XSSFSheet sheet, int rowNum, int colNum) {
        XSSFRow row = sheet.getRow(rowNum);
        if (Objects.isNull(row))
            return null;
        XSSFCell cell = row.getCell(colNum);
        DataFormatter formatter = new DataFormatter();
        String data;
        try {
            data = formatter.formatCellValue(cell);

        } catch (Exception e) {
            data = "";
        }
        return data;
    }

    private int getCellCount(XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(1);
        return row.getLastCellNum();
    }

    private int getRowCount(XSSFSheet sheet) {
        return sheet.getLastRowNum();
    }

    private XSSFSheet getSheet(InputStream inputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        if (Objects.isNull(this.sheetName)) {
            return workbook.getSheetAt(0);
        } else {
            return workbook.getSheet(this.sheetName);
        }
    }

}
