package com.trilogy.dcm.pages.client;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class ClientPage extends BasePage {
    //Messages
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Validating...successful";

    // Frame Locators
    private static final String FRAME_CONTAINER = "frame[name='container']";
    private static final String FRAME_CACHEFRAME = "iframe[name='cacheframe0']";
    private static final String FRAME_PROPPAGE = "frame[name='proppage']";
    private static final String FRAME_SUBPAGE = "frame[name='subpage']";

    //Party Selector
    private static final String LOCATOR_LOCATOR_PARTY_IFRAME = "iframe[name='OrgPartySearch_search_div_frame']";
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";
    private static final String LOCATOR_PARTY_BUTTON = "//a[@id='Party']";
    private static final String LOCATOR_PARTY_DROPDOWN = "//button[contains(@class, 'dropdown-toggle') and @title='Search Person']";
    private static final String LOCATOR_PARTY_DROPDOWN_LIST = "//span[@class='text' and text()='Search Client']";
    // Client
    private static final String LOCATOR_CREATE_CLIENT = "[id=\"Button_Client_Main_NewClient\"]";
    private static final String LOCATOR_CLIENT_ID = "//input[@id=\"ClientID\"]";
    private static final String LOCATOR_CLIENT_NAME = "//input[@id=\"ClientName\"]";
    private static final String LOCATOR_RADIO_BUTTON_CREATE_NEW_CONTACT_PERSON = "//input[@value='Create New Contact Point']/parent::label";
    private static final String LOCATOR_STREET1 = "//input[@id=\"Street1\"]";
    private static final String LOCATOR_CITY = "//input[@id=\"City\"]";
    private static final String LOCATOR_POSTAL_CODE = "[id=\"ZipCode\"]";
    private static final String LOCATOR_VALIDATE_BUTTON = "text=Validate";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    private static final String LOCATOR_COUNTRY = "[data-id='Country']";
    private static final String LOCATOR_COUNTRY_TEXT = "//div[contains(@class, 'btn-group') and contains(@class, 'bootstrap-select') and contains(@class, 'form-control') and contains(@class, 'open')]//ul[contains(@class, 'dropdown-menu')]//span[@class='text' and normalize-space()='%s']";
    private static final String LOCATOR_STATE_TEXT = "//div[contains(@class, 'btn-group') and contains(@class, 'bootstrap-select') and contains(@class, 'form-control') and contains(@class, 'open')]//ul[contains(@class, 'dropdown-menu')]//span[@class='text' and normalize-space()='%s']";
    private static final String LOCATOR_STATE = "[data-id=\"US_State\"]";
    private static final String LOCATOR_VALIDATE_SUCCESSMSG = "//*[text()='Validating...successful']";
    private static final String LOCATOR_VALIDATING_CLIENT_ID = "//table[@id=\"Grid_Client_Main\"]//tr[@class=\"active\"]//td[@id=\"Field_Client_Main_ClientID_Grid_0\"]";
    private static final String LOCATOR_VALIDATING_CLIENT_UPDATED_NAME = "//table[@id='Grid_Client_Main']//tr[contains(@class, 'active')]/td[@id='Field_Client_Main_ClientName_Grid_0']";
    //Search Client ID
    private static final String LOCATOR_SEARCH_CLIENT_ID = "//form[@id=\"Search_Client_Main_primaryForm\"]//*[@placeholder=\"Client ID\"]";
    private static final String LOCATOR_SEARCH_BUTTON = "//a[text()='Search']";
    //Edit Client ID
    private static final String LOCATOR_EDIT_BUTTON = "//a[@id=\"Button_Client_Main_EditClient\"]";


    public ClientPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

 public void clickSearchClient(){
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME,FRAME_SUBPAGE);
     isElementPresent(LOCATOR_PARTY_DROPDOWN);
     clickElement( LOCATOR_PARTY_DROPDOWN, "clickStateDropDown");
     isElementPresent(LOCATOR_PARTY_DROPDOWN_LIST);
     clickElement( LOCATOR_PARTY_DROPDOWN_LIST, "clickSearchClient");
  }
    public void clickCreateClient() {
        clickElement( LOCATOR_CREATE_CLIENT, "clickCreateClient");
    }
    public void enterClientId(String clientId){
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME,FRAME_PROPPAGE);
        setTextElement(LOCATOR_CLIENT_ID, clientId , "clientID");
    }
    public void enterClientName(String clientName){
         setTextElement(LOCATOR_CLIENT_NAME, clientName, "clientName");
    }
    public void clickCreateNewContactPerson(){
        clickElement(LOCATOR_RADIO_BUTTON_CREATE_NEW_CONTACT_PERSON,"createNewPersonContact");
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
    public void clickValidateButton() {
        clickElement(LOCATOR_VALIDATE_BUTTON, "Validate button");
    }
    public void clickSaveButton() {
        clickElement(LOCATOR_SAVE_BUTTON, "Save button");
    }
    public String retrieveToastMessage() {
        return getText(LOCATOR_VALIDATE_SUCCESSMSG, "Validation message");
    }
    // Search
    public void enterSearchClientId(String searchClientId){
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME,FRAME_SUBPAGE);
        setTextElement(LOCATOR_SEARCH_CLIENT_ID,searchClientId,"Search ClientId");
    }
    public void clickSearchButton(){
        clickElement(LOCATOR_SEARCH_BUTTON, "Search Button");
    }
    public boolean isValidationMessageClientIdDisplayed(String searchClientId) {
        return isElementPresent(String.format(LOCATOR_VALIDATING_CLIENT_ID,searchClientId ));
    }
    public boolean isValidationMessageClientNameDisplayed(String newClientName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME,FRAME_SUBPAGE);
        return isElementPresent( String.format(LOCATOR_VALIDATING_CLIENT_UPDATED_NAME, newClientName));
    }
    public void clickEditButton(){
        clickElement(LOCATOR_EDIT_BUTTON,"Click edit client Button");
    }
    public void editClientName(String newClientName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME,FRAME_PROPPAGE);
        clickElement( LOCATOR_CLIENT_NAME, "Click Client Name for Editing");
        setTextElement( LOCATOR_CLIENT_NAME, newClientName, "Edited Last name");
    }



}
