package com.trilogy.dcm.pages.contractKit;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class ContractKitPage extends BasePage {

    public static final String EXPECTED_MSG_FOR_VALIDATE = "Validating...successful";
//Frame Locators
    private static final String FRAME_CONTAINER = "frame[name='container']";
    private static final String FRAME_CACHEFRAME1 = "iframe[name='cacheframe1']";
    private static final String FRAME_PROPPAGE = "frame[name='proppage']";
    private static final String FRAME_SUBPAGE = "frame[name='subpage']";
    private static final String FRAME_SIDE_BAR = "frame[name='sidebar']";

 //Field Locators
    private static final String LOCATOR_CONTRACT_KIT = "//a[@id='Contracts_sub']";
    private static final String LOCATOR_CREATE_CONTRACT_KIT = "//a[@id='Button_Contracts_Main_NewContractKit']";
    private static final String LOCATOR_CONTRACT_KIT_NAME = "//input[@id=\"Name\"]";
    private static final String LOCATOR_CONTRACT_KIT_DESCRIPTION = "//textarea[@id=\"Description\"]";
    private static final String LOCATOR_VALIDATE_BUTTON = "text=Validate";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    private static final String LOCATOR_VALIDATE_SUCCESSMSG = "//*[text()='Validating...successful']";
    private static final String LOCATOR_SEARCH_TEXTBOX = "//div[@id=\"primary_search_field\"]//input[@id=\"Field_Contracts_Main_Name_Search_Value\"]";
    private static final String LOCATOR_SEARCH_BUTTON = "//div[@id=\"Search_Contracts_Main_SearchFormDiv\"]//*[text()='Search']";
    private static final String LOCATOR_VALIDATING_CONTRACT_NAME = "//table[@id=\"Grid_Contracts_Main\"]//tr[@class=\"active\"]//td[@id=\"Field_Contracts_Main_Name_Grid_0\"]";

    public ContractKitPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

   public void clickContractKit(){
        getWebDriver().switchToFrames(FRAME_SIDE_BAR);
        clickElement(LOCATOR_CONTRACT_KIT,"click contract kit");
   }
   public void clickCreateContractKit(){
         getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE);
         clickElement(LOCATOR_CREATE_CONTRACT_KIT,"Click Create Contract Kit");
   }
  public void enterContractName(String contractKitName){
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE);
        setTextElement(LOCATOR_CONTRACT_KIT_NAME,contractKitName,"Enter ContractKit Name");
  }
  public void enterDescription(String description){
         setTextElement(LOCATOR_CONTRACT_KIT_DESCRIPTION,description,"Enter ContractKit Description");
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

    public void entersearchContractName(String searchContractName){
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE);
        setTextElement(LOCATOR_SEARCH_TEXTBOX,searchContractName,"Search ContractKit Name");
    }
    public void clickSearchButton(){
        clickElement(LOCATOR_SEARCH_BUTTON,"search button");
   }
    public boolean isValidationContractNameDisplayed(String searchContractName) {
        String xpath = String.format(LOCATOR_VALIDATING_CONTRACT_NAME, searchContractName);
        return isElementPresent( xpath);
    }

}
