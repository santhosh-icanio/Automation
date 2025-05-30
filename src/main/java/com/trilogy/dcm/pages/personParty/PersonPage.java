/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

// PersonPage.java
package com.trilogy.dcm.pages.personParty;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class PersonPage extends BasePage {
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Validating...successful";

    // Frame Locators
    private static final String FRAME_CONTAINER = "frame[name='container']";
    private static final String FRAME_CACHEFRAME = "iframe[name='cacheframe0']";
    private static final String FRAME_PROPPAGE = "frame[name='proppage']";
    private static final String FRAME_SUBPAGE = "frame[name='subpage']";

    // Field Locators
    private static final String LOCATOR_CREATE_PERSON_LINK = "#Button_Person_Main_NewPerson";
    private static final String LOCATOR_FIRST_NAME = "#Party\\.FirstName";
    private static final String LOCATOR_PREFERRED_NAME = "#Party\\.PreferredName";
    private static final String LOCATOR_MIDDLE_NAME = "#Party\\.MiddleName";
    private static final String LOCATOR_LAST_NAME = "#Party\\.LastName";
    private static final String LOCATOR_DTCC_ID = "#DTCCID";
    private static final String LOCATOR_NPN = "#Party\\.NPN";
    private static final String LOCATOR_SYNCHRONIZE_PDB = "[data-id=\"SyncPDB\"]";
    private static final String LOCATOR_SYNCHRONIZE_OPTION = "//div[@class=\"btn-group bootstrap-select form-control open\"]//div[contains(@class,'dropdown-menu') and contains(@class,'open')]//ul[contains(@class,'dropdown-menu inner')]//span[normalize-space(text())='No']";
    private static final String LOCATOR_TAX_ID = "//input[@name='Party.TaxID']";
    private static final String LOCATOR_DISTRIBUTOR_SPAN = "//*[@class=\"form-group custom checkbox\"]//*[text()='Distributor']";
    private static final String LOCATOR_STREET1 = "#ContactPoint\\.Address\\.Street1";
    private static final String LOCATOR_CITY = "#ContactPoint\\.Address\\.City";
    private static final String LOCATOR_COUNTRY = "[data-id='ContactPoint.Address.Country']";
    private static final String LOCATOR_COUNTRY_TEXT = "//div[contains(@class, 'btn-group') and contains(@class, 'bootstrap-select') and contains(@class, 'form-control') and contains(@class, 'open')]//ul[contains(@class, 'dropdown-menu')]//span[@class='text' and normalize-space()='%s']";
    private static final String LOCATOR_START_DATE = "//input[@id='NewStatus.StartDate']";
    private static final String LOCATOR_STATE_TEXT = "//div[contains(@class, 'btn-group') and contains(@class, 'bootstrap-select') and contains(@class, 'form-control') and contains(@class, 'open')]//ul[contains(@class, 'dropdown-menu')]//span[@class='text' and normalize-space()='%s']";
    private static final String LOCATOR_STATE = "[data-id=\"US_State\"]";
    private static final String LOCATOR_POSTAL_CODE = "[id=\"ZipCode\"]";
    private static final String LOCATOR_VALIDATE_BUTTON = "text=Validate";
    private static final String LOCATOR_VALIDATE_SUCCESSMSG = "//*[text()='Validating...successful']";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    //Search
    private static final String LOCATOR_PARTY_ID = "//div[@id=\"primary_search_field\"]//input[@id=\"Field_Person_Main_PartyID_Search_Value\"]";
    private static final String LOCATOR_VALIDATING_PARTY_ID = "//table[@id=\"Grid_Person_Main\"]//tr[@class=\"active\"]//td[@id=\"Field_Person_Main_PartyID_Grid_0\"]";
    private static final String LOCATOR_VALIDATING_LASTNAME = "//table[@id=\"Grid_Person_Main\"]//tr[@class=\"active\"]//td[@id=\"Field_Person_Main_LastName_Grid_0\"]";
    private static final String LOCATOR_SEARCH_BUTTON = "//a[text()='Search']";
    //Edit Person Party
    private static final String LOCATOR_EDIT_BUTTON = "[id=\"Button_Person_Main_BasicInfo_EditGrid\"]";


    public PersonPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    // Create Person Form Methods
    public boolean clickCreatePerson() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_CREATE_PERSON_LINK, "Create Person");
        getWebDriver().waitForLoad(WebDriver.LoadType.LOAD);
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_PROPPAGE);
        return isElementPresent(LOCATOR_FIRST_NAME);
    }

    public void enterFirstName(String firstName) {
         setTextElement(LOCATOR_FIRST_NAME, firstName, "First Name");
    }

    public void enterMiddleName(String middleName) {
         setTextElement(LOCATOR_MIDDLE_NAME, middleName, "Middle name");
    }

    public void enterPreferredName(String preferredName) {
         setTextElement(LOCATOR_PREFERRED_NAME, preferredName, "Preferred Name");
    }

    public void enterLastName(String lastName) {
         setTextElement(LOCATOR_LAST_NAME, lastName, "Last Name");
    }

    public void enterDTCCid(String dtccId) {
         setTextElement(LOCATOR_DTCC_ID, dtccId, "DTCC");
    }

    public void enterNPN(String npn) {
         setTextElement(LOCATOR_NPN, npn, "NPN");
    }

    public void selectSynchroize() {
        clickElement(LOCATOR_SYNCHRONIZE_PDB, "synchronize");
        clickElement(LOCATOR_SYNCHRONIZE_OPTION, "synchronize Option");
    }

    public void enterTaxID(int taxId) {
         setTextElement(LOCATOR_TAX_ID, String.valueOf(taxId), "tax Id");
    }

    public void clickDistributor() {
        clickElement(LOCATOR_DISTRIBUTOR_SPAN, "select distributor in applicable roles");
    }

    public void enterStreet1(String street1) {
         setTextElement(LOCATOR_STREET1, street1, "street1");
    }

    public void enterCity(String city) {
         setTextElement(LOCATOR_CITY, city, "city");
    }

    public void selectCountry(String country) {
        clickElement(LOCATOR_COUNTRY, "Country");
        clickElement(String.format(LOCATOR_COUNTRY_TEXT, country), "Country Name " + country);
    }

    public void selectState(String state) {
        clickElement(LOCATOR_STATE, "State");
        clickElement(String.format(LOCATOR_STATE_TEXT, state), "State Name " + state);
    }

    public void enterPostalCode(String postalCode) {
        setTextElement(LOCATOR_POSTAL_CODE, postalCode, "postal code");
    }

    public void enterStartDate(String startDate) {
         setTextElement(LOCATOR_START_DATE, startDate, "New Party Start Date (" + startDate + ")");
    }

    public void clickValidateButton() {
        clickElement(LOCATOR_VALIDATE_BUTTON, "Validate button");
    }

    public void clickSaveButton() {
        clickElement(LOCATOR_SAVE_BUTTON, "Save button");
    }

    public String retrieveToastMessage() {
        return getText(LOCATOR_VALIDATE_SUCCESSMSG, "Validation message");
    }

    public void enterPartyId(String partyId) {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHEFRAME, FRAME_SUBPAGE);
        clickElement(LOCATOR_PARTY_ID, "Party Id");
        setTextElement(LOCATOR_PARTY_ID, partyId, "Party Id");
    }

    public void clickSearchButton() {
        clickElement(LOCATOR_SEARCH_BUTTON, "Search button");
    }

    public boolean isValidatingPartyIdDisplayed(String partyId) {
        String xpath = String.format(LOCATOR_VALIDATING_PARTY_ID, partyId);
        return isElementPresent(xpath);
    }
    public void clickEditBasicInfo() {
        clickElement(LOCATOR_EDIT_BUTTON, "edit basic info button");
    }
    public void editLastName(String newLastName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME,FRAME_PROPPAGE);
        clickElement(LOCATOR_LAST_NAME, "last name");
         setTextElement(LOCATOR_LAST_NAME, newLastName, "new last name");
    }
    public boolean isValidationUpdatedLastNameDisplayed(String newLastName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME,FRAME_SUBPAGE);
        String xpath = String.format(LOCATOR_VALIDATING_LASTNAME, newLastName);
        return isElementPresent(xpath);
    }
}
