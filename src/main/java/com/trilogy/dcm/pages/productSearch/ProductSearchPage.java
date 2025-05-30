/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.pages.productSearch;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class ProductSearchPage extends BasePage {

    //Message
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Validating...successful";

    //Frame Locator
    private static final String FRAME_CONTAINER = "frame[name='container']";
    private static final String FRAME_CACHE_FRAME = "iframe[name='cacheframe1']";
    private static final String FRAME_SUBPAGE = "frame[name='subpage']";
    private static final String FRAME_PROP_PAGE = "frame[name='proppage']";
    private static final String FRAME_COMPONENT = "iframe[name='component_iframe']";

    //Party Selector
    private static final String LOCATOR_SUB_PRODUCT_SEARCH_FRAME = "frame[name='sidebar']";
    private static final String LOCATOR_HIERARCHY_BUTTON = "//a[@id='Hierarchy']";
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";
    private static final String LOCATOR_SUB_PRODUCT_SEARCH = "//a[@id='ProductHierarchySearch_sub']";
    private static final String LOCATOR_SEARCH_DROPdOWN = "//button[@title='Search Prod Hierarchy']";
    private static final String LOCATOR_SEARCH_PRODUCT = "//*[@class='dropdown-menu open']//*[text()= 'Search Product']";
    private static final String LOCATOR_PRODUCT_SEARCH_NAME = "//*[@name='Search_SCCMProductSearch_Main_primary_display_div']//button[contains(@class, 'dropdown-toggle')]";

    //Create Product
    private static final String LOCATOR_NEW_PRODUCT_BUTTON = "//a[@id= 'Button_SCCMProductSearch_SCCMProduct_NewSCCMProduct']";
    private static final String LOCATOR_PRODUCT_NAME = "//input[@id = 'Name']";
    private static final String LOCATOR_PRODUCT_DESCRIPTION = "//input[@id = 'Description']";
    private static final String LOCATOR_PRODUCT_TYPE_DROPDOWN = "//button[@data-id='ProductType']";
    private static final String LOCATOR_PRODUCT_TYPE = "//*[@class='btn-group bootstrap-select form-control open']//*[@class='dropdown-menu open']//*[contains(text(), 'DIS - Disability')]";
    private static final String LOCATOR_VALIDATE_BUTTON = "//a[@id= 'validate']";
    private static final String LOCATOR_VALIDATION = "//*[text()='Validating...successful']";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    private static final String LOCATOR_BASIC_SEARCH_NAME = "//div[@id='primary_search_field']//input[@placeholder='Name']";
    private static final String LOCATOR_TABLE_NAME = "//*[@id = 'Inspector_SCCMProductDetails_Details_div_out']//*[text()='%s']";
    private static final String LOCATOR_EDIT_PRODUCT_DETAILS_BUTTON = "//a[@id = 'Button_SCCMProductSearch_SCCMProduct_EditSCCMProduct']";

    public ProductSearchPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickHierarchy() {
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        isElementPresent(LOCATOR_HIERARCHY_BUTTON);
        clickElement(LOCATOR_HIERARCHY_BUTTON, "Party");
        getWebDriver().waitForLoad();
    }

    public void clickSubProductSearch() {
        getWebDriver().switchToFrames(LOCATOR_SUB_PRODUCT_SEARCH_FRAME);
        isElementPresent(LOCATOR_SUB_PRODUCT_SEARCH);
        clickElement(LOCATOR_SUB_PRODUCT_SEARCH, "sub product search");
    }

    public void selectProduct() {
        getWebDriver().waitForLoad();
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_SEARCH_DROPdOWN, "searchDropDown");
        getWebDriver().waitForLoad();
        isElementPresent(LOCATOR_SEARCH_DROPdOWN);
        clickElement(LOCATOR_SEARCH_PRODUCT, "searchProduct");
    }

    public boolean isTitleNameVisible() {
        return isElementPresent(LOCATOR_PRODUCT_SEARCH_NAME);
    }

    public void clickNewProductButton() {
        clickElement(LOCATOR_NEW_PRODUCT_BUTTON, "create Product");
    }

    public void clickProductName() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_PROP_PAGE);
        clickElement(LOCATOR_PRODUCT_NAME, "productName");
    }

    public void enterProductName(String name) {
        setTextElement(LOCATOR_PRODUCT_NAME, name, "enterProductName");
    }

    public void clickProductDescription() {
        clickElement(LOCATOR_PRODUCT_DESCRIPTION, "productDescription");
    }

    public void enterProductDescription(String description) {
        setTextElement(LOCATOR_PRODUCT_DESCRIPTION, description, "enterProductDescription");
    }

    public void clickProductTypeDropDown() {
        clickElement(LOCATOR_PRODUCT_TYPE_DROPDOWN, "productTypeDropDown");
    }

    public void clickProductType() {
        clickElement(LOCATOR_PRODUCT_TYPE, "productType");
    }

    public void clickValidateButton() {
        clickElement(LOCATOR_VALIDATE_BUTTON, "validateButton");
    }

    public String retrieveMessage() {
        return getText(LOCATOR_VALIDATION, "Validation element");
    }

    public void clickSaveButton() {
        clickElement(LOCATOR_SAVE_BUTTON, "saveButton");
    }

    public void clickBasicSearchName() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_BASIC_SEARCH_NAME, "basicSearchName");
    }

    public void enterBasicSearchName(String name) {
        setTextElement(LOCATOR_BASIC_SEARCH_NAME, name, "enterBasicSearchName");
        getWebDriver().waitForLoad();
    }

    public void clickEditProductDetailsButton() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_EDIT_PRODUCT_DETAILS_BUTTON, "editProduct");
        getWebDriver().waitForLoad();
    }

    public boolean isValidationMessageDisplayed(String searchName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_SUBPAGE, FRAME_COMPONENT);
        String xpath = String.format(LOCATOR_TABLE_NAME, searchName);
        return isElementPresent(xpath);
    }
}
