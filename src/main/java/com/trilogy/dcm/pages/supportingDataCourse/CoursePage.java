package com.trilogy.dcm.pages.supportingDataCourse;

import com.microsoft.playwright.FrameLocator;
import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class CoursePage extends BasePage {

    //Messages
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Validating...successful";

    // Frame Locators
    private static final String FRAME_CONTAINER = "frame[name='container']";
    private static final String FRAME_CACHEFRAME = "iframe[name='cacheframe2']";
    private static final String FRAME_CACHEFRAME1 = "iframe[name='cacheframe1']";
    private static final String FRAME_PROPPAGE = "frame[name='proppage']";
    private static final String FRAME_SUBPAGE = "frame[name='subpage']";
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";
    private static final String LOCATOR_SIDE_BAR = "frame[name='sidebar']";

    //Field Locators
    private static final String LOCATOR_SUPPORTING_DATA = "//a[@id=\"SupportingData_sub\"]";
    private static final String LOCATOR_PARTY_DROPDOWN = "//button[contains(@class, 'dropdown-toggle') and @title='Search Course']";
    private static final String LOCATOR_PARTY_DROPDOWN_LIST = "//span[@class='text' and text()='Search Course']";
    private static final String LOCATOR_NEW_COURSE_BUTTON = "//a[@id=\"Button_CourseManagement_Main_New\"]";
    private static final String LOCATOR_COURSE_NAME = "//input[@id=\"Name\"]";
    private static final String LOCATOR_COURSE_NUMBER = "//input[@id=\"CourseNumber\"]";
    private static final String LOCATOR_ADD_A_SOURCE_BUTTON = "//button[@name=\"CourseSources_add\"]";
    private static final String LOCATOR_COURSE_SOURCES_ID = "//input[@id=\"CourseSourcesNew_CourseSourceId_0\"]";
    private static final String LOCATOR_JURISDICTION = "//button[@data-id=\"CourseSourceJurisdiction_0_\"]";
    private static final String LOCATOR_JURISDICTION_LIST_TEXT = "//div[contains(@class, 'btn-group') and contains(@class, 'bootstrap-select') and contains(@class, 'open')]//ul[contains(@class, 'dropdown-menu')]//span[@class='text' and normalize-space()='%s']";
    private static final String LOCATOR_VALIDATE_BUTTON = "text=Validate";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    private static final String LOCATOR_VALIDATE_SUCCESSMSG = "//*[text()='Validating...successful']";
    private static final String LOCATOR_COURSE_SEARCH = "//form[@id=\"Search_CourseManagement_Main_primaryForm\"]//input[@id=\"Field_CourseManagement_Main_CourseName_Search_Value\"]";
    private static final String LOCATOR_SEARCH_BUTTON = "//a[text()='Search']";
    private static final String LOCATOR_VALIDATING_COURSE_NAME = "//table[@id=\"Grid_CourseManagement_Main\"]//tr[@class=\"active\"]//td[@id='Field_CourseManagement_Main_CourseName_Grid_0']";
    private static final String LOCATOR_EDIT_COURSE_BUTTON = "//a[@id='Button_CourseManagement_Main_Edit']";
    private static final String LOCATOR_VALIDATING_UPDATED_COURSE_NUMBER = "//table[@id=\"Grid_CourseManagement_Main\"]//tr[@class=\"active\"]//td[@id=\"Field_CourseManagement_Main_CourseNumber_Grid_0\"]";

    public CoursePage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickSupportingData(){
        getWebDriver().switchToFrames(LOCATOR_SIDE_BAR);
        isElementPresent(LOCATOR_SUPPORTING_DATA);
        clickElement(LOCATOR_SUPPORTING_DATA,"click supporting data");
    }
    public void selectSearchCourse() {
    getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE);
        clickElement(LOCATOR_PARTY_DROPDOWN, "clickSearchDropdown");
        clickElement( LOCATOR_PARTY_DROPDOWN_LIST, "clickSearchCourse");
    }
    public void clickNewCourse(){
        clickElement( LOCATOR_NEW_COURSE_BUTTON, "clickNewCourse");
    }
    public void enterCourseName(String courseName){
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE);
        setTextElement(LOCATOR_COURSE_NAME,courseName,"courseName");
    }
    public void enterCourseNumber(String courseNumber){
        setTextElement(LOCATOR_COURSE_NUMBER,courseNumber,"courseNumber");
    }
    public void clickAddASource(){
        clickElement(LOCATOR_ADD_A_SOURCE_BUTTON,"clickSourceButton");
    }
    public void enterSourceId(String sourceId){
        setTextElement(LOCATOR_COURSE_SOURCES_ID,sourceId,"sourceId");
    }
    public void selectJurisdiction(String jurisdiction) {
        clickElement(LOCATOR_JURISDICTION, "Jurisdiction");
        clickElement(String.format(LOCATOR_JURISDICTION_LIST_TEXT, jurisdiction), "Jurisdiction Name " + jurisdiction);
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
    public void enterSearchTextbox(String searchCourse){
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE);
        setTextElement(LOCATOR_COURSE_SEARCH, searchCourse,"enter Course Name");
    }
    public void clickSearchButton(){
        clickElement(LOCATOR_SEARCH_BUTTON,"ClickSearch");
    }
    public boolean isValidationCourseNameDisplayed(String searchCourse) {
        return isElementPresent( String.format(LOCATOR_VALIDATING_COURSE_NAME, searchCourse));
    }
    public void clickEditCourse(String newCourseNumber){
        clickElement(LOCATOR_EDIT_COURSE_BUTTON,"Click Edit Course");
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_PROPPAGE);
        setTextElement( LOCATOR_COURSE_NUMBER, newCourseNumber, "Edited Course Number");
    }
    public boolean isValidationCourseNumberDisplayed(String newCourseNumber) {
        getWebDriver().switchToFrames(FRAME_CONTAINER,FRAME_CACHEFRAME1,FRAME_SUBPAGE);
        String xpath = String.format(LOCATOR_VALIDATING_UPDATED_COURSE_NUMBER, newCourseNumber);
        return isElementPresent( String.format(LOCATOR_VALIDATING_UPDATED_COURSE_NUMBER, newCourseNumber));
    }


}
