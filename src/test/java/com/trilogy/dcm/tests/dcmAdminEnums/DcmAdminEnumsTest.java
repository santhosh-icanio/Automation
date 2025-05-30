/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.dcmAdminEnums;

import com.trilogy.dcm.pages.dcmAdminEnums.DcmAdminEnumsPage;
import com.trilogy.dcm.tests.BaseTest;
import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.tests.CommonTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners({TestListenerAdapter.class})
@Slf4j
public class DcmAdminEnumsTest extends BaseTest {

    private final DcmAdminEnumsPage dcmAdminEnumsPage;
    private final LoginPage loginPage;

    public DcmAdminEnumsTest() {
        super("DCM Admin Enums");
        dcmAdminEnumsPage = new DcmAdminEnumsPage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    public void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        dcmAdminEnumsPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(com.trilogy.dcm.tests.login.LoginData.getLoginData(), loginPage);
    }

    @Test(testName = "dCM Admin Enums", priority = 1)
    public void dcmAdminEnumsPage(){
        try{
            dcmAdminEnumsPage.clickDcmAdmin();
            dcmAdminEnumsPage.clickSupportingData();
            dcmAdminEnumsPage.selectEnums();
            Assert.assertTrue(dcmAdminEnumsPage.isTitleNameVisible(), "Search key value is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "create Enums", priority = 2,dataProvider = DcmAdminEnumsData.LOCATION_NEW_ENUMS_DATA_SHEET, dataProviderClass = DcmAdminEnumsData.class)
    public void createEnums(String basicInfo,String enumName,String value,String displayName){
        try {
            dcmAdminEnumsPage.clickCreateEnumsButton();
            dcmAdminEnumsPage.enterBasicInfoButton(basicInfo);
            dcmAdminEnumsPage.clickAddButton();
            dcmAdminEnumsPage.clickEnumName();
            dcmAdminEnumsPage.enterEnumName(enumName);
            dcmAdminEnumsPage.clickValue();
            dcmAdminEnumsPage.enterValue(value);
            dcmAdminEnumsPage.clickDisplayName();
            dcmAdminEnumsPage.enterDisplayName(displayName);
            dcmAdminEnumsPage.clickValidateButton();
            dcmAdminEnumsPage.clickValidateButton();
            String actualSuccessMessage = dcmAdminEnumsPage.retrieveMessage();
            Assert.assertEquals(actualSuccessMessage, DcmAdminEnumsPage.EXPECTED_MSG_FOR_VALIDATE, "Validation success message is not displayed");
            dcmAdminEnumsPage.clickSaveButton();
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "search & Edit Enums", priority = 3,dataProvider = DcmAdminEnumsData.LOCATOR_EDIT_AND_SEARCH_DATA_SHEET, dataProviderClass = DcmAdminEnumsData.class)
    public void searchAndEditEnums(String enumerationID,String editValue){
        try{
            dcmAdminEnumsPage.clickEnumerationID();
            dcmAdminEnumsPage.enterEnumerationID(enumerationID);
            dcmAdminEnumsPage.clickSearchButton();
            Assert.assertTrue(dcmAdminEnumsPage.isValidationMessageDisplayed(enumerationID), "Search key value is not displayed");
            dcmAdminEnumsPage.clickEditEnumButton();
            dcmAdminEnumsPage.clickEditValue();
            dcmAdminEnumsPage.enterEditValue(editValue);
            dcmAdminEnumsPage.clickValidateButton();
            dcmAdminEnumsPage.clickValidateButton();
            String actualSuccessMessage = dcmAdminEnumsPage.retrieveMessage();
            Assert.assertEquals(actualSuccessMessage, DcmAdminEnumsPage.EXPECTED_MSG_FOR_VALIDATE, "Validation success message is not displayed");
            dcmAdminEnumsPage.clickSaveButton();
        } catch (Exception e) {
            fail(e);
        }
    }
}