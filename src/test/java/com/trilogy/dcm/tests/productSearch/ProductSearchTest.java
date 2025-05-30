/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.productSearch;

import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.pages.productSearch.ProductSearchPage;
import com.trilogy.dcm.tests.BaseTest;
import com.trilogy.dcm.tests.CommonTest;
import com.trilogy.dcm.tests.login.LoginData;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners({TestListenerAdapter.class})
@Slf4j
public class ProductSearchTest extends BaseTest {

    private final ProductSearchPage productSearchPage;
    private final LoginPage loginPage;

    public ProductSearchTest() {
        super("Product Search");
        productSearchPage = new ProductSearchPage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    public void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        productSearchPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(LoginData.getLoginData(), loginPage);
    }

    @Test(testName = "Product Search", priority = 1)
    public void productSearch() {
        try {
            productSearchPage.clickHierarchy();
            productSearchPage.clickSubProductSearch();
            productSearchPage.selectProduct();
            Assert.assertTrue(productSearchPage.isTitleNameVisible(), "Search key value is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "create New Product", priority = 2, dataProvider = ProductSearchData.LOCATION_NEW_PRODUCT_DATA_SHEET, dataProviderClass = ProductSearchData.class)
    public void createNewProduct(String name, String description) {
        try {
            productSearchPage.clickNewProductButton();
            productSearchPage.clickProductName();
            productSearchPage.enterProductName(name);
            productSearchPage.clickProductDescription();
            productSearchPage.enterProductDescription(description);
            productSearchPage.clickProductTypeDropDown();
            productSearchPage.clickProductType();
            productSearchPage.clickValidateButton();
            String actualSuccessMessage = productSearchPage.retrieveMessage();
            Assert.assertEquals(actualSuccessMessage, ProductSearchPage.EXPECTED_MSG_FOR_VALIDATE, "Validation success message is not displayed");
            productSearchPage.clickSaveButton();
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "verify the search and edit process for product", priority = 3, dataProvider = ProductSearchData.LOCATION_PRODUCT_SEARCH_DATA_SHEET, dataProviderClass = ProductSearchData.class)
    public void verifyTheSearchAndEditProcessForProduct(String name, String editName) {
        try {
            productSearchPage.clickBasicSearchName();
            productSearchPage.enterBasicSearchName(name);
            Assert.assertTrue(productSearchPage.isValidationMessageDisplayed(name), "Search key value is not displayed");
            productSearchPage.clickEditProductDetailsButton();
            productSearchPage.clickProductName();
            productSearchPage.enterProductName(editName);
            productSearchPage.clickValidateButton();
            String actualSuccessMessage = productSearchPage.retrieveMessage();
            Assert.assertEquals(actualSuccessMessage, ProductSearchPage.EXPECTED_MSG_FOR_VALIDATE, "Validation success message is not displayed");
            productSearchPage.clickSaveButton();
        } catch (Exception e) {
            fail(e);
        }
    }
}