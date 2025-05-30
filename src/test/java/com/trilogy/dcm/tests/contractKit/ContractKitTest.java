package com.trilogy.dcm.tests.contractKit;

import com.trilogy.dcm.pages.compensation.CompensationPage;
import com.trilogy.dcm.pages.contractKit.ContractKitPage;
import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.tests.BaseTest;
import com.trilogy.dcm.tests.CommonTest;
import com.trilogy.dcm.tests.login.LoginData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ContractKitTest extends BaseTest {

    private final LoginPage loginPage;
    private final CompensationPage compensationPage;
    private final ContractKitPage contractKitPage;

    public ContractKitTest() {
        super("ContractKit");
        compensationPage = new CompensationPage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
        contractKitPage = new ContractKitPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    public void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        compensationPage.setCurrentTestReportNode(getCurrentTestReportNode());
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        contractKitPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(LoginData.getLoginData(), loginPage);
        compensationPage.isCompensationElementPresent();
        compensationPage.clickCompensation();
    }

    @Test(testName = "Create Contract Kit", dataProvider = ContractKitData.CONTRACTKIT_DATA_SHEET, dataProviderClass = ContractKitData.class, priority = 1)
    public void createContractKit(String contractKitName, String description) {
        try {
            contractKitPage.clickContractKit();
            contractKitPage.clickCreateContractKit();
            contractKitPage.enterContractName(contractKitName);
            contractKitPage.enterDescription(description);
            contractKitPage.clickValidateButton();
            String actualSuccessMsg = contractKitPage.retrieveToastMessage();
            Assert.assertEquals(actualSuccessMsg, contractKitPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
            contractKitPage.clickSaveButton();
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "Search Contract Kit", dataProvider = ContractKitData.CONTRACTKITSEARCH_DATA_SHEET, dataProviderClass = ContractKitData.class, priority = 2)
    public void searchContractKit(String searchContractName) {
        try {
            contractKitPage.entersearchContractName(searchContractName);
            contractKitPage.clickSearchButton();
            boolean isVisible = contractKitPage.isValidationContractNameDisplayed(searchContractName);
            Assert.assertTrue(isVisible, "Search Contract name is not displayed");

        } catch (Exception e) {
            fail(e);
        }
    }

}