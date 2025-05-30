/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.pages.dcmAdminEnums;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class DcmAdminEnumsPage extends BasePage {

    //message
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Validating...successful";

    //party Selector
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";

    //frame container
    private static final String FRAME_CONTAINER = "frame[name='container']";
    private static final String FRAME_CACHE_FRAME = "iframe[name='cacheframe1']";
    private static final String FRAME_SUBPAGE = "frame[name='subpage']";
    private static final String FRAME_PROP_PAGE = "frame[name='proppage']";

    //party Selector
    private static final String LOCATOR_DCM_ADMIN_BUTTON = "//a[@id= 'DCM Admin']";
    private static final String LOCATOR_SUB_PRODUCT_SEARCH_FRAME = "frame[name='sidebar']";
    private static final String LOCATOR_SUB_SUPPORTING_DATA = "//a[@id= 'SupportingData_sub']";
    private static final String LOCATOR_COURSE_DROPDOWN = "//button[@title= 'Search Course']";
    private static final String LOCATOR_ENUMS_SEARCH = "//*[@class='dropdown-menu open']//*[text()= 'Search Enums']";
    private static final String LOCATOR_ENUMS_SEARCH_NAME = "//*[@id='menuSelect']";

    //create Enums
    private static final String LOCATOR_CREATE_ENUMS_BUTTON = "//a[@id= 'Button_EnumManager_Enums_Main_CreateEnum']";
    private static final String LOCATOR_BASIC_INFO_BUTTON = "//input[@id= 'Id']";
    private static final String LOCATOR_ADD_BUTTON = "//button[@name='Entries_add']";
    private static final String LOCATOR_CREATE_ENUMS = "//input[@id= 'Entries_Name_0']";
    private static final String LOCATOR_VALUE = "//input[@id= 'Entries_Value_0']";
    private static final String LOCATOR_DISPLAY_NAME = "//input[@id= 'Entries_DisplayName_0']";
    private static final String LOCATOR_VALIDATE_BUTTON = "//a[@id= 'validate']";
    private static final String LOCATOR_VALIDATION = "//*[text()='Validating...successful']";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    private static final String LOCATOR_ENUMERATION_ID = "//div[@id='primary_search_field']//input[@id= 'Field_EnumManager_Enums_Main_ID_Search_Value']";
    private static final String LOCATOR_SEARCH_BUTTON = "//*[@id='Search_EnumManager_Enums_Main_SearchFormDiv']//*[text()='Search']";
    private static final String LOCATOR_TABLE_NAME = "//td[@id= 'Field_EnumManager_Enums_Main_ID_Grid_0']";
    private static final String LOCATOR_EDIT_ENUM_BUTTON = "//a[@id= 'Button_EnumManager_Enums_Main_EditEnum']";
    private static final String LOCATOR_EDIT_VALUE = "//input[@id= 'EntriesEdit_Value_0']";

    public DcmAdminEnumsPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickDcmAdmin() {
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        isElementPresent(LOCATOR_DCM_ADMIN_BUTTON);
        clickElement(LOCATOR_DCM_ADMIN_BUTTON, "DcmAdmin");
    }

    public void clickSupportingData() {
        getWebDriver().switchToFrames(LOCATOR_SUB_PRODUCT_SEARCH_FRAME);
        isElementPresent(LOCATOR_SUB_SUPPORTING_DATA);
        clickElement(LOCATOR_SUB_SUPPORTING_DATA, "sub supporting data");
    }

    public void selectEnums() {
        isElementPresent(LOCATOR_COURSE_DROPDOWN);
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_COURSE_DROPDOWN, "CourseDropDown");
        isElementPresent(LOCATOR_ENUMS_SEARCH);
        clickElement(LOCATOR_ENUMS_SEARCH, "enumsSearch");
    }

    public boolean isTitleNameVisible() {
        return isElementPresent(LOCATOR_ENUMS_SEARCH_NAME);
    }

    public void clickCreateEnumsButton() {
        clickElement(LOCATOR_CREATE_ENUMS_BUTTON, "createEnums");
    }

    public void enterBasicInfoButton(String basicInfo) {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_PROP_PAGE);
        setTextElement(LOCATOR_BASIC_INFO_BUTTON, basicInfo, "basicInfo");
    }

    public void clickAddButton() {
        clickElement(LOCATOR_ADD_BUTTON, "add");
    }

    public void clickEnumName() {
        clickElement(LOCATOR_CREATE_ENUMS, "enumName");
    }

    public void enterEnumName(String enumName) {
        setTextElement(LOCATOR_CREATE_ENUMS, enumName, "enumName");
    }

    public void clickValue() {
        clickElement(LOCATOR_VALUE, "value");
    }

    public void enterValue(String value) {
        setTextElement(LOCATOR_VALUE, value, "value");
    }

    public void clickDisplayName() {
        clickElement(LOCATOR_DISPLAY_NAME, "displayName");
    }

    public void enterDisplayName(String displayName) {
        setTextElement(LOCATOR_DISPLAY_NAME, displayName, "displayName");
    }

    public void clickValidateButton() {
        isElementPresent(LOCATOR_VALIDATE_BUTTON);
        getWebDriver().waitForLoad();
        clickElement(LOCATOR_VALIDATE_BUTTON, "validateButton");
    }

    public String retrieveMessage() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_PROP_PAGE);
        return getText(LOCATOR_VALIDATION, "Validation element");
    }

    public void clickSaveButton() {
        clickElement(LOCATOR_SAVE_BUTTON, "saveButton");
    }

    public void clickEnumerationID() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_ENUMERATION_ID, "enumerationID");
    }

    public void enterEnumerationID(String enumerationID) {
        setTextElement(LOCATOR_ENUMERATION_ID, enumerationID, "enumerationID");
    }

    public void clickSearchButton() {
        clickElement(LOCATOR_SEARCH_BUTTON, "searchButton");
    }

    public boolean isValidationMessageDisplayed(String enumerationID) {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_SUBPAGE);
        return isElementPresent(LOCATOR_TABLE_NAME);
    }

    public void clickEditEnumButton() {
        clickElement(LOCATOR_EDIT_ENUM_BUTTON, "editEnumsButton");
    }

    public void clickEditValue() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE_FRAME, FRAME_PROP_PAGE);
        clickElement(LOCATOR_EDIT_VALUE, "editValue");
    }

    public void enterEditValue(String editValue) {
        setTextElement(LOCATOR_EDIT_VALUE, editValue, "editValue");
    }
}
