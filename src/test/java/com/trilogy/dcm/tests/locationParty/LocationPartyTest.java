/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.locationParty;

import com.trilogy.dcm.pages.locationParty.LocationPage;
import com.trilogy.dcm.tests.BaseTest;
import com.trilogy.dcm.tests.login.LoginData;
import com.trilogy.dcm.pages.login.LoginPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.TestListenerAdapter;
import com.trilogy.dcm.tests.CommonTest;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners({TestListenerAdapter.class})
@Slf4j
public class LocationPartyTest extends BaseTest {

    private final LoginPage loginPage;
    private final LocationPage locationPage;

    public LocationPartyTest() {
        super("Location Party");
        loginPage = new LoginPage(getWebDriver());
        locationPage = new LocationPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    public void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        locationPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(LoginData.getLoginData(), loginPage);
    }

    @Test(testName = "Location Party Search", priority = 1)
    public void searchLocationParty() {
        try {
            locationPage.clickParty();
            locationPage.clickDropDown();
            Assert.assertTrue(locationPage.isTitleVisible(), "Search key value is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "Location Party Create", priority = 2, dataProvider = LocationPartyData.LOCATION_PARTY_DATA_SHEET, dataProviderClass = LocationPartyData.class)
    public void createLocationParty(String name, String id, String dtcCId, String faxNumber, String physicalStreet, String physicalCity, String zipCode) {
        try {
            locationPage.clickCreateLocation();
            locationPage.clickName();
            locationPage.enterName(name);
            locationPage.clickId();
            locationPage.enterId(id);
            locationPage.clickSearchOwningFirm();
            locationPage.clickSearchButton();
            locationPage.clickOrganizationName();
            locationPage.clickUseSelectedOrganization();
            locationPage.clickDTCCId();
            locationPage.enterDTCCId(dtcCId);
            locationPage.clickFaxNumber();
            locationPage.enterFaxNumber(faxNumber);
            locationPage.clickPhysicalStreet1();
            locationPage.enterPhysicalStreet1(physicalStreet);
            locationPage.clickPhysicalCity();
            locationPage.enterPhysicalCity(physicalCity);
            locationPage.clickZipCode();
            locationPage.enterZipCode(zipCode);
            locationPage.clickValidateButton();
            String actualSuccessMessage = locationPage.retrieveMessage();
            Assert.assertEquals(actualSuccessMessage, LocationPage.EXPECTED_MSG_FOR_VALIDATE, "Validation success message is not displayed");
            locationPage.clickSaveButton();
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "Search & Edit Location Verify", priority = 3, dataProvider = LocationPartyData.LOCATION_SEARCH_DATA_SHEET, dataProviderClass = LocationPartyData.class)
    public void searchAndEditLocationVerify(String name, String editName) {
        try {
            locationPage.clickBasicSearchName();
            locationPage.enterBasicSearchName(name);
            locationPage.clickEditSearchButton();
            boolean isVisible = locationPage.isValidationMessageDisplayed(name);
            Assert.assertTrue(isVisible, "Search key value is not displayed");
            locationPage.clickEditBasicInfoButton();
            locationPage.clickEditName();
            locationPage.enterEditName(editName);
            locationPage.clickValidateButton();
            String actualSuccessMessage = locationPage.retrieveMessage();
            Assert.assertEquals(actualSuccessMessage, LocationPage.EXPECTED_MSG_FOR_VALIDATE, "Validation success message is not displayed");
            locationPage.clickSaveButton();
        } catch (Exception e) {
            fail(e);
        }
    }
}