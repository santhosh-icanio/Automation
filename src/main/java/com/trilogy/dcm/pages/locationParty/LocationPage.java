/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */
package com.trilogy.dcm.pages.locationParty;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class LocationPage extends BasePage {

    //Messages
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Validating...successful";

    // Frame Locators
    public static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";
    public static final String LOCATOR_PARTY_BUTTON = "//a[@id='Party']";
    private static final String FRAME_CONTAINER = "frame[name='container']";
    private static final String FRAME_CACHEFRAME = "iframe[name='cacheframe0']";
    private static final String FRAME_PROPPAGE = "frame[name='proppage']";
    private static final String FRAME_SUBPAGE = "frame[name='subpage']";
    private static final String FRAME_COMPONENT = "iframe[name='component_iframe']";
    //Party Selector
    private static final String LOCATOR_LOCATOR_PARTY_IFRAME = "iframe[name='OrgPartySearch_search_div_frame']";
    private static final String LOCATOR_PARTY_DROPDOWN = "//button[contains(@class, 'dropdown-toggle') and @title='Search Person']";
    private static final String LOCATOR_PARTY_DROPDOWN_LIST = "//span[@class='text' and text()='Search Location']";
    private static final String LOCATOR_SEARCH_NAME = "//*[@id='Search_Location_Main_primary_display_div']//button[contains(@class, 'dropdown-toggle')]/span[@class= 'filter-option pull-left']";
    //Location
    private static final String LOCATOR_CREATE_LOCATION = "//a[@id='Button_Location_Main_NewLocation']";
    private static final String LOCATOR_NAME = "//input[@id ='Party.CurrentDetails.Name']";
    private static final String LOCATOR_ID = "//input[@id ='Unid']";
    private static final String LOCATOR_SEARCH_OWNING_FIRM = "//a[@id= 'searchOrgPartySearch_search_div']";
    private static final String LOCATOR_SEARCH_BUTTON = "(//*[contains(@class,'search-container')]//*[@class='nav nav-pills navbar-right']//*[text()='Search'])[last()]";
    private static final String LOCATOR_ORGANIZATION_NAME = "//td[@id= 'Field_Party_Name_Grid_0']";
    private static final String LOCATOR_SELECTED_ORGANIZATION = "//a[@id ='Button_PartySearch_PP_Select']";
    private static final String LOCATOR_DTCC_ID = "//input[@id= 'DTCCID']";
    private static final String LOCATOR_FAX_NUMBER = "//input[@id= 'ContactPoint.Address.Fax']";
    private static final String LOCATOR_PHYSICAL_STREET1 = "//input[@id= 'ContactPoint.Address.Street1']";
    private static final String LOCATOR_PHYSICAL_CITY = "//input[@id= 'ContactPoint.Address.City']";
    private static final String LOCATOR_ZIP_CODE = "//input[@id= 'ZipCode']";
    private static final String LOCATOR_VALIDATE_BUTTON = "//a[@id= 'validate']";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    //Search
    private static final String LOCATOR_BASIC_SEARCH_NAME = "//*[@name='Search_Location_Main_primaryForm']//*[@placeholder='Name']";
    private static final String LOCATOR_EDIT_NAME = "//input[@id= 'Party.CurrentDetails.Name']";
    private static final String LOCATOR_EDIT_INFO_BUTTON = "//a[@id= 'Button_Location_Main_EditLocation']";
    private static final String LOCATOR_VALIDATION = "//*[text()='Validating...successful']";
    private static final String LOCATOR_TABLE_NAME = "//*[@id='Inspector_Location_Main_BasicInfo_rollup_div']//*[text()='%s']";

    public LocationPage(WebDriver<?> webDriver) {
        super(webDriver);
    }


    public void clickParty() {
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        clickElement(LOCATOR_PARTY_BUTTON, "Party");
        getWebDriver().waitForLoad();
    }

    public void clickDropDown() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_PARTY_DROPDOWN, "stateDropDown");
        isElementPresent(LOCATOR_PARTY_DROPDOWN);
        clickElement(LOCATOR_PARTY_DROPDOWN_LIST, "searchLocation");
        getWebDriver().waitForLoad();
    }

    public boolean isTitleVisible() {
        return isElementPresent(LOCATOR_SEARCH_NAME);
    }


    public void clickCreateLocation() {
        clickElement(LOCATOR_CREATE_LOCATION, "createLocation");
    }

    public void clickName() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_PROPPAGE);
        clickElement(LOCATOR_NAME, "createLocation");
    }

    public void enterName(String name) {
        setTextElement(LOCATOR_NAME, name, "enterName");
    }

    public void clickId() {
        clickElement(LOCATOR_ID, "Id");
    }

    public void enterId(String id) {
        setTextElement(LOCATOR_ID, id, "enterId");
    }

    public void clickSearchOwningFirm() {
        clickElement(LOCATOR_SEARCH_OWNING_FIRM, "searchOwningFirm");
    }

    public void clickSearchButton() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_PROPPAGE, LOCATOR_LOCATOR_PARTY_IFRAME);
        clickElement(LOCATOR_SEARCH_BUTTON, "searchButton");
    }

    public void clickOrganizationName() {
        clickElement(LOCATOR_ORGANIZATION_NAME, "organizationName");
    }

    public void clickUseSelectedOrganization() {
        clickElement(LOCATOR_SELECTED_ORGANIZATION, "useSelectedOrganization");
    }

    public void clickDTCCId() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_PROPPAGE);
        clickElement(LOCATOR_DTCC_ID, "DTCCId");
    }

    public void enterDTCCId(String dtccId) {
        setTextElement(LOCATOR_DTCC_ID, dtccId, "enterDTCCId");
    }

    public void clickFaxNumber() {
        clickElement(LOCATOR_FAX_NUMBER, "faxNumber");
    }

    public void enterFaxNumber(String faxNumber) {
        setTextElement(LOCATOR_FAX_NUMBER, faxNumber, "enterFaxNumber");
    }

    public void clickPhysicalStreet1() {
        clickElement(LOCATOR_PHYSICAL_STREET1, "physicalStreet1");
    }

    public void enterPhysicalStreet1(String physicalStreet) {
        setTextElement(LOCATOR_PHYSICAL_STREET1, physicalStreet, "enterPhysicalStreet1");
    }

    public void clickPhysicalCity() {
        clickElement(LOCATOR_PHYSICAL_CITY, "physicalCity");
    }

    public void enterPhysicalCity(String physicalCity) {
        setTextElement(LOCATOR_PHYSICAL_CITY, physicalCity, "enterPhysicalCity");
    }

    public void clickZipCode() {
        clickElement(LOCATOR_ZIP_CODE, "zipCode");
    }

    public void enterZipCode(String zipCode) {
        setTextElement(LOCATOR_ZIP_CODE, zipCode, "enterZipCode");
    }

    public void clickValidateButton() {
        clickElement(LOCATOR_VALIDATE_BUTTON, "validateButton");
    }

    public void clickSaveButton() {
        clickElement(LOCATOR_SAVE_BUTTON, "saveButton");
    }

    public void clickBasicSearchName() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_BASIC_SEARCH_NAME, "basicSearchName");
    }

    public void enterBasicSearchName(String name) {
        setTextElement(LOCATOR_BASIC_SEARCH_NAME, name, "enterBasicSearchName");
    }

    public void clickEditSearchButton() {
        clickElement(LOCATOR_SEARCH_BUTTON, "editSearchButton");
        getWebDriver().waitForLoad();
    }

    public void clickEditBasicInfoButton() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_SUBPAGE);
        isElementPresent(LOCATOR_EDIT_INFO_BUTTON);
        clickElement(LOCATOR_EDIT_INFO_BUTTON, "editBasicInfoButton");
        getWebDriver().waitForLoad();
    }

    public void clickEditName() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_PROPPAGE);
        clickElement(LOCATOR_EDIT_NAME, "editName");
    }

    public void enterEditName(String editName) {
        setTextElement(LOCATOR_EDIT_NAME, editName, "enterEditName");
    }

    public String retrieveMessage() {
        return getText(LOCATOR_VALIDATION, "Validation element");
    }

    public boolean isValidationMessageDisplayed(String searchNme) {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_SUBPAGE, FRAME_COMPONENT);
        String xpath = String.format(LOCATOR_TABLE_NAME, searchNme);
        return isElementPresent(xpath);
    }
}