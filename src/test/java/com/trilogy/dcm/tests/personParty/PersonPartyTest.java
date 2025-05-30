/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.personParty;

import com.trilogy.dcm.listeners.TestListener;
import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.pages.party.PartyPage;
import com.trilogy.dcm.pages.personParty.PersonPage;
import com.trilogy.dcm.tests.BaseTest;
import com.trilogy.dcm.tests.CommonTest;
import com.trilogy.dcm.tests.login.LoginData;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners({TestListener.class})
@Slf4j
public class PersonPartyTest extends BaseTest {

    private final LoginPage loginPage;
    private final PersonPage personPage;
    private final PartyPage partyPage;

    public PersonPartyTest() {
        super("PersonParty");
        personPage = new PersonPage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
        partyPage = new PartyPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    public void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        personPage.setCurrentTestReportNode(getCurrentTestReportNode());
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        partyPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(LoginData.getLoginData(), loginPage);
        partyPage.isPartyElementPresent();
        partyPage.clickParty();
    }

    @Test(testName = "Create Person", dataProvider = PersonPartyData.PERSONPARTY_DATA_SHEET, dataProviderClass = PersonPartyData.class, priority = 1)
    public void createPerson(String firstName, String preferredName, String middleName, String lastName,
                             String dtccId, String npn, String street1, String city,String country, String postalCode,String state,String startDate) {
        try {
            boolean isClickCreatePerson = personPage.clickCreatePerson();
            Assert.assertTrue(isClickCreatePerson,"Create Person Button Clicked");
            personPage.enterFirstName(firstName);
            personPage.enterPreferredName(preferredName);
            personPage.enterMiddleName(middleName);
            personPage.enterLastName(lastName);
            personPage.enterDTCCid(dtccId);
            personPage.enterNPN(npn);
            personPage.selectSynchroize();
            personPage.enterStartDate(startDate);
            int taxId = personPage.randomTaxID();
            personPage.enterTaxID(taxId);
            personPage.clickDistributor();
            personPage.enterStreet1(street1);
            personPage.enterCity(city);
            personPage.selectCountry(country);
            personPage.selectState(state);
            personPage.enterPostalCode(postalCode);
            personPage.clickValidateButton();
            String actualSuccessMsg = personPage.retrieveToastMessage();
            Assert.assertEquals(actualSuccessMsg, PersonPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
            personPage.clickSaveButton();
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test(testName = "Search Person", dataProvider = PersonPartyData.SEARCHPERSONPARTY_DATA_SHEET, dataProviderClass = PersonPartyData.class, priority = 2)
    public void searchPerson(String partyId){
     try{
         personPage.enterPartyId(partyId);
         personPage.clickSearchButton();
         boolean isVisible = personPage.isValidatingPartyIdDisplayed(partyId);
         Assert.assertTrue(isVisible, "Search Party ID is not displayed");
     }
     catch (Exception e) {
         fail(e);
     }
    }

    @Test(testName = "Edit Person", dataProvider = PersonPartyData.EDITPERSONPARTY_DATA_SHEET, dataProviderClass = PersonPartyData.class, priority = 3)
    public void editPerson(String newLastName){
        try{
            personPage.clickEditBasicInfo();
            personPage.editLastName(newLastName);
            personPage.clickValidateButton();
            String actualSuccessMsg = personPage.retrieveToastMessage();
            Assert.assertEquals(actualSuccessMsg, PersonPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
            personPage.clickSaveButton();
            boolean isVisible = personPage.isValidationUpdatedLastNameDisplayed(newLastName);
            Assert.assertTrue(isVisible, "Edited Last name is not displayed");
        }
        catch (Exception e) {
            fail(e);
        }
    }


}
