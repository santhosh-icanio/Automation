package com.trilogy.dcm.tests.components;

import com.trilogy.dcm.pages.compensation.CompensationPage;
import com.trilogy.dcm.pages.components.ComponentPage;
import com.trilogy.dcm.pages.contractKit.ContractKitPage;
import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.tests.BaseTest;
import com.trilogy.dcm.tests.CommonTest;
import com.trilogy.dcm.tests.SupportingDataCourse.CourseData;
import com.trilogy.dcm.tests.contractKit.ContractKitData;
import com.trilogy.dcm.tests.login.LoginData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ComponentTest extends BaseTest {

    private final LoginPage loginPage;
    private final ComponentPage componentPage;
    private final CompensationPage compensationPage;
    private final ContractKitPage contractKitPage;

    public ComponentTest() {
        super("Component");
        compensationPage = new CompensationPage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
        componentPage = new ComponentPage(getWebDriver());
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
        componentPage.setCurrentTestReportNode(getCurrentTestReportNode());
        contractKitPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(LoginData.getLoginData(), loginPage);
        compensationPage.isCompensationElementPresent();
        compensationPage.clickCompensation();
        contractKitPage.clickContractKit();
    }

    @Test(testName = "Create Component",dataProvider = ComponentData.CREATECOMPONENT_DATA_SHEET, dataProviderClass = ComponentData.class, priority = 1)
    public void createComponent(String searchContractName,String componentName,String description, String label, String quotaSearch,String PaymentSearch) {
        try {
         componentPage.clickComponent();
         componentPage.enterSearchContractName(searchContractName);
         componentPage.clickSearchButton();
         boolean isVisible = componentPage.isValidationContractNameDisplayed(searchContractName);
         Assert.assertTrue(isVisible, "Search Contract name is not displayed");
         componentPage.clickNewComponentButton();
         String randomlyComponentName = componentPage.randomName(componentName,"DCM_COMPONENT_");
         componentPage.enterComponentName(randomlyComponentName);
         componentPage.enterComponentDescription(description);
         componentPage.clickAddLabelButton();
         componentPage.enterlabel(label);
         componentPage.clickAddQuotaButton();
         componentPage.clickSearchQuotaButton();
         componentPage.enterQuotaSearch(quotaSearch);
         componentPage.clickQuotaSelectSearchButton();
         isVisible = componentPage.isValidationQuotaNameDisplayed(quotaSearch);
         Assert.assertTrue(isVisible, "Search Quota Name is not displayed");
         componentPage.clickSelectButton();
         componentPage.clickDirectPayment();
         componentPage.clickSelectDirectPayment();
         componentPage.enterDirectPayment(PaymentSearch);
         componentPage.clickDirectPaymentSearch();
         isVisible = componentPage.isValidationCDirectPaymentaNameDisplayed(PaymentSearch);
         Assert.assertTrue(isVisible, "Search Direct payment Name is not displayed");
         componentPage.clickDirectPaymentSelect();
         componentPage.clickValidateButton();
         String actualSuccessMsg = componentPage.retrieveToastMessage();
         Assert.assertEquals(actualSuccessMsg, ComponentPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
         componentPage.clickSaveButton();

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "Search Component", dataProvider = ComponentData.SEARCHCOMPONENT_DATA_SHEET, dataProviderClass = ComponentData.class, priority = 2)
    public void searchComponent(String componentValue) {
        try {
            componentPage.componentTopSearch();
            componentPage.enterComponentSearchValue(componentValue);
            componentPage.ComponentValueSearchButton();
            boolean isVisible = componentPage.isValidationSearchComponentNameDisplayed(componentValue);
            Assert.assertTrue(isVisible, "Search Component Name is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test(testName = "Edit Component", dataProvider = ComponentData.EDITCOMPONENT_DATA_SHEET, dataProviderClass = ComponentData.class, priority = 3)
    public void editComponent(String newLabelName) {
        try {
            componentPage.clickEditButton();
            componentPage.editComponentName(newLabelName);
            componentPage.clickValidateButton();
            String actualSuccessMsg = componentPage.retrieveToastMessage();
            Assert.assertEquals(actualSuccessMsg, ComponentPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
            componentPage.clickSaveButton();
            boolean isVisible = componentPage.isValidationSearchEditedLabelNameDisplayed(newLabelName);
            Assert.assertTrue(isVisible, "Search Edited Label Name is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }

}
