/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.productSearch;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class ProductSearchData extends BaseData {
    public static final String LOCATION_NEW_PRODUCT_DATA_SHEET = "newProduct";
    public static final String LOCATION_PRODUCT_SEARCH_DATA_SHEET = "productSearch";

    @org.testng.annotations.DataProvider(name = LOCATION_NEW_PRODUCT_DATA_SHEET)
    public static Object[][] getNewProductSearchData() {
        return getTestDataJSON(ConfigManager.getConfig().getNewProductSearchFile(),
                LOCATION_NEW_PRODUCT_DATA_SHEET);
    }

    @org.testng.annotations.DataProvider(name = LOCATION_PRODUCT_SEARCH_DATA_SHEET)
    public static Object[][] getProductSearchData() {
        return getTestDataJSON(ConfigManager.getConfig().getProductSearchFile(),
                LOCATION_PRODUCT_SEARCH_DATA_SHEET);
    }
}