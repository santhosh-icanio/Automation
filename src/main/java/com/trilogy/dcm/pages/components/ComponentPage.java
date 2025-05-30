package com.trilogy.dcm.pages.components;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class ComponentPage extends BasePage {
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Validating...successful";
//Frames
private static final String FRAME_CONTAINER = "frame[name='container']";
    private static final String FRAME_CACHEFRAME1 = "iframe[name='cacheframe1']";
    private static final String FRAME_PROPPAGE = "frame[name='proppage']";
    private static final String FRAME_SUBPAGE = "frame[name='subpage']";
    private static final String I_FRAME_CONTAINER = "iframe[name='component_iframe']";
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";
    private static final String LOCATOR_SIDE_BAR = "frame[name='sidebar']";
    private static final String QUOTA_FRAME = "iframe[name='QuotasSearch_search_div_frame']";
    private static final String DIRECTPAYMENT_FRAME = "iframe[name='DirectPaymentsSearch_search_div_frame']";
//Field Locators
private static final String LOCATOR_VALIDATE_BUTTON = "text=Validate";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    private static final String LOCATOR_VALIDATE_SUCCESSMSG = "//*[text()='Validating...successful']";
    private static final String LOCATOR_COMPENSATION = "//a[@id='Compensation Setup']";
    private static final String LOCATOR_CONTRACT_KIT = "//a[@id='Contracts_sub']";
    private static final String LOCATOR_COMPONENTS = "//ul[@id='sideMenu']//a[@id='Tab_Contracts_Main_Components_link']";
    private static final String LOCATOR_SEARCH_TEXTBOX = "//div[@id='primary_search_field']//input[@id='Field_Contracts_Main_Name_Search_Value']";
    private static final String LOCATOR_SEARCH_BUTTON = "//div[@id='Search_Contracts_Main_SearchFormDiv']//*[text()='Search']";
    private static final String LOCATOR_NEWCOMPONENT_BUTTON = "//a[@id='Button_Contracts_Main_Components_NewComponent']";
    private static final String LOCATOR_VALIDATING_CONTRACT_NAME = "//table[@id='Grid_Contracts_Main']//tr[@class='active']//td[@id='Field_Contracts_Main_Name_Grid_0']";
    private static final String LOCATOR_COMPONENT_NAME = "//input[@id='Name']";
    private static final String LOCATOR_COMPONENT_DESCRIPTION = "//textarea[@id='Description']";
    private static final String LOCATOR_ADD_COMPONENTLABEL_BUTTON = "//button[@name='Labels_add']";
    private static final String LOCATOR_COMPONENT_LABEL = "//input[@id='Labels_Value_0']";
    private static final String LOCATOR_ADD_QUOTA_BUTTON = "//button[@name='Quotas_add']";
    private static final String LOCATOR_SELECT_QUOTA_BUTTON = "//span[@id='complexField_QuotasSearch_search_div']";
    private static final String LOCATOR_SELECT_QUOTA = "//input[@id='Field_Quotas_Search_Name_Search_Value']";
    private static final String LOCATOR_QUOTA_SEARCH_BUTTON = "//*[@class='search-container form-inline row picker-search']//a[text()='Search']";
    private static final String LOCATOR_VALIDATING_QUOTA_NAME = "//table[@id='QuotasGrid']//tr[@class='active']//td[@id='Field_Quotas_Search_Name_Grid_0']";
    private static final String LOCATOR_QUOTA_SELECT_BUTTON = "//a[@id='QuotasSearchButton_PP_Select']";
    private static final String LOCATOR_SELECTED_QUOTA_VALIDATING = "//*[@class='picker-field-text picker-field-textQuotas_Item_GID_0']";
    private static final String LOCATOR_ADD_DIRECT_PAYMENT = "//button[@name='DirectPayments_add']";
    private static final String LOCATOR_SELECT_DIRECT_PAYMENT = "//span[@id='complexField_DirectPaymentsSearch_search_div']";
    private static final String LOCATOR_SEARCH_DIRECT_PAYMENT_TEXTBOX = "//input[@id='Field_DirectPayments_Search_Name_Search_Value']";
    private static final String LOCATOR_SEARCH_DIRECT_PAYMENT_BUTTON = "//*[@class='search-container form-inline row picker-search']//a[text()='Search']";
    private static final String LOCATOR_VALIDATING_DIRECT_PAYMENT_NAME = "//table[@id='DirectPaymentsGrid']//tr[@class='active']//td[@id='Field_DirectPayments_Search_Name_Grid_0']";
    private static final String LOCATOR_DIRECT_PAYMENT_SELECT_BUTTON = "//*[@id='DirectPaymentsSearchButton_PP_Select']";
    private static final String LOCATOR_COMPONENT_TOP_SEARCH = "//a[@aria-controls='Search_Contracts_Main_Components_SearchFormDiv']";
    private static final String LOCATOR_COMPONENT_SEARCH_VALUE = "//input[@id='Field_Contracts_Main_Components_Name_Search_Value']";
    private static final String LOCATOR_COMPONENT_SEARCH_BUTTON = "//*[@id='Search_Contracts_Main_Components_SearchFormDiv']//a[text()='Search']";
    private static final String LOCATOR_VALIDATING_COMPONENT_NAME = "//table[@id='Grid_Contracts_Main_Components']//tr[@class='active']//td[@id='Field_Contracts_Main_Components_Name_Grid_0']";
    private static final String LOCATOR_EDIT_COMPONENT_DETAILS = "//form[@id='Button_Contracts_Main_Components_EditComponent_form']";
    private static final String LOCATOR_VALIDATING_LABEL_NAME = "//table[@id='Grid_Contracts_Main_Components']//tr[@class='active']//td[@id='Field_Contracts_Main_Components_Labels_Grid_0']";


    public ComponentPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickComponent(){
        getWebDriver().switchToFrames(LOCATOR_SIDE_BAR);
        isElementPresent(LOCATOR_COMPONENTS);
        clickElement(LOCATOR_COMPONENTS,"click component");
    }
    public boolean enterSearchContractName(String searchContractName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE);
        return setTextElement( LOCATOR_SEARCH_TEXTBOX, searchContractName, "Search Contract Name");
    }
    public void clickSearchButton() {
        clickElement( LOCATOR_SEARCH_BUTTON, "Search Button");
    }
    public boolean isValidationContractNameDisplayed(String searchContractName) {
        return isElementPresent(String.format(LOCATOR_VALIDATING_CONTRACT_NAME, searchContractName));
    }
    public void clickNewComponentButton() {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE,I_FRAME_CONTAINER);
        clickElement( LOCATOR_NEWCOMPONENT_BUTTON, "Click New Component Button");
    }
    public void enterComponentName(String componentName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE);
        setTextElement( LOCATOR_COMPONENT_NAME, componentName, "enter Component Name");
    }
    public void enterComponentDescription(String description) {
        setTextElement(LOCATOR_COMPONENT_DESCRIPTION, description, "Enter Component Description");
    }

    public void clickAddLabelButton() {
        clickElement(LOCATOR_ADD_COMPONENTLABEL_BUTTON, "Click Add Label");
    }
    public void enterlabel(String label) {
        setTextElement(LOCATOR_COMPONENT_LABEL, label, "Enter Label Name");
    }
    public void clickAddQuotaButton() {
        clickElement(LOCATOR_ADD_QUOTA_BUTTON, "Click Add Quota");
    }

    public void clickSearchQuotaButton() {
        clickElement( LOCATOR_SELECT_QUOTA_BUTTON, "Click Search Quota");
    }

    public void enterQuotaSearch(String quotaSearch) {
       getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE,QUOTA_FRAME);
        setTextElement(LOCATOR_SELECT_QUOTA, quotaSearch, "Selecting a Quota");
    }

    public void clickQuotaSelectSearchButton() {
        clickElement(LOCATOR_QUOTA_SEARCH_BUTTON, "ClickSearchButton");
    }

    public boolean isValidationQuotaNameDisplayed(String quotaSearch) {
        return isElementPresent(String.format(LOCATOR_VALIDATING_QUOTA_NAME, quotaSearch));
    }
    public void clickSelectButton() {
        clickElement(LOCATOR_QUOTA_SELECT_BUTTON, "Select Quota");
    }
    public void clickDirectPayment() {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE);
        clickElement(LOCATOR_ADD_DIRECT_PAYMENT, "Click Add Direct Payment");
    }

    public void clickSelectDirectPayment() {
        clickElement( LOCATOR_SELECT_DIRECT_PAYMENT, "Click Select Direct Payment");
    }

    public void enterDirectPayment(String directPaymentSearch) {
         getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE,DIRECTPAYMENT_FRAME);
        setTextElement( LOCATOR_SEARCH_DIRECT_PAYMENT_TEXTBOX, directPaymentSearch, "Enter Direct payment");
    }

    public void clickDirectPaymentSearch() {
        clickElement( LOCATOR_SEARCH_DIRECT_PAYMENT_BUTTON, "Click Search Direct Payment");
    }

    public boolean isValidationCDirectPaymentaNameDisplayed(String PaymentSearch) {
        return isElementPresent(String.format(LOCATOR_VALIDATING_DIRECT_PAYMENT_NAME, PaymentSearch));
    }

    public void clickDirectPaymentSelect() {
        clickElement( LOCATOR_DIRECT_PAYMENT_SELECT_BUTTON, "Click Select Button");
    }
    public void clickValidateButton() {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE);
        clickElement(LOCATOR_VALIDATE_BUTTON, "Validate button");
    }
    public void clickSaveButton() {
        clickElement(LOCATOR_SAVE_BUTTON, "Save button");
    }
    public String retrieveToastMessage() {
        return getText(LOCATOR_VALIDATE_SUCCESSMSG, "Validation message");
    }
    public void componentTopSearch() {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE,I_FRAME_CONTAINER);
        clickElement(LOCATOR_COMPONENT_TOP_SEARCH, "Click the outer Search");
    }

    public void enterComponentSearchValue(String componentValue) {
        setTextElement( LOCATOR_COMPONENT_SEARCH_VALUE, componentValue, "Enter Component Value");
    }

    public void ComponentValueSearchButton() {
        clickElement( LOCATOR_COMPONENT_SEARCH_BUTTON, "click Search Button");
    }

    public boolean isValidationSearchComponentNameDisplayed(String componentValue) {
        return isElementPresent( String.format(LOCATOR_VALIDATING_COMPONENT_NAME, componentValue));
    }

    public void clickEditButton() {
        clickElement( LOCATOR_EDIT_COMPONENT_DETAILS, "Click Edit Button");
    }

    public void editComponentName(String newLabelName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE);
        clickElement( LOCATOR_COMPONENT_LABEL, "Click Label Name for Editing");
        setTextElement( LOCATOR_COMPONENT_LABEL, newLabelName, "Edited Label name");
    }
    public boolean isValidationSearchEditedLabelNameDisplayed(String newLabelName) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE,I_FRAME_CONTAINER);
        return isElementPresent( String.format(LOCATOR_VALIDATING_LABEL_NAME, newLabelName));
    }

}
