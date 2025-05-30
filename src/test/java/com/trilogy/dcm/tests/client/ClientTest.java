package com.trilogy.dcm.tests.client;

import com.trilogy.dcm.pages.client.ClientPage;
import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.pages.party.PartyPage;
import com.trilogy.dcm.tests.BaseTest;
import com.trilogy.dcm.tests.CommonTest;
import com.trilogy.dcm.tests.login.LoginData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ClientTest extends BaseTest {

    private final LoginPage loginPage;
    private final ClientPage clientPage;
    private final PartyPage partyPage;

    public ClientTest() {
        super("Client");
        clientPage = new ClientPage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
        partyPage = new PartyPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    public void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        clientPage.setCurrentTestReportNode(getCurrentTestReportNode());
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        partyPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(LoginData.getLoginData(), loginPage);
        partyPage.isPartyElementPresent();
        partyPage.clickParty();
        clientPage.clickSearchClient();
    }
    @Test(testName = "Create Client", dataProvider = ClientData.CLIENT_DATA_SHEET, dataProviderClass = ClientData.class, priority = 1)
    public void createClient(String clientId, String clientName, String street1,String city,String country,String state, String postalCode) {
        try {
            clientPage.clickCreateClient();
            clientPage.enterClientId(clientId);
            clientPage.enterClientName(clientName);
            clientPage.clickCreateNewContactPerson();
            clientPage.enterStreet1(street1);
            clientPage.enterCity(city);
            clientPage.selectCountry(country);
            clientPage.selectState(state);
            clientPage.enterPostalCode(postalCode);
            clientPage.clickValidateButton();
            String actualSuccessMsg = clientPage.retrieveToastMessage();
            Assert.assertEquals(actualSuccessMsg, clientPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
            clientPage.clickSaveButton();
        } catch (Exception e) {
            fail("client creation failed due to exception: " + e.getMessage());
        }
    }
    @Test(testName = "Search Client", dataProvider = ClientData.CLIENTSEARCH_DATA_SHEET, dataProviderClass = ClientData.class, priority = 3)
    public void searchClient(String searchClientId) throws InterruptedException {
        clientPage.enterSearchClientId(searchClientId);
        clientPage.clickSearchButton();
        boolean isVisible = clientPage.isValidationMessageClientIdDisplayed(searchClientId);
        Assert.assertTrue(isVisible, "Search Client ID is not displayed");
    }

    @Test(testName = "Edit Client", dataProvider = ClientData.CLIENTEDIT_DATA_SHEET, dataProviderClass = ClientData.class, priority = 4)
    public void editClient(String newClientName) throws InterruptedException {
        clientPage.clickEditButton();
        clientPage.editClientName(newClientName);
        clientPage.clickValidateButton();
        String actualSuccessMsg = clientPage.retrieveToastMessage();
        Assert.assertEquals(actualSuccessMsg, clientPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
        clientPage.clickSaveButton();
        boolean isVisible = clientPage.isValidationMessageClientNameDisplayed(newClientName);
        Assert.assertTrue(isVisible, "Search Client Name is not displayed");
    }



}
