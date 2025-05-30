package com.trilogy.dcm.tests.SupportingDataCourse;

import com.trilogy.dcm.pages.dcmAdmin.DCMAdminPage;
import com.trilogy.dcm.pages.login.LoginPage;
import com.trilogy.dcm.pages.supportingDataCourse.CoursePage;
import com.trilogy.dcm.tests.BaseTest;
import com.trilogy.dcm.tests.CommonTest;
import com.trilogy.dcm.tests.login.LoginData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class CourseTest extends BaseTest {
    private final LoginPage loginPage;
    private final CoursePage coursePage;
    private final DCMAdminPage dcmAdminPage;

    public CourseTest() {
        super("SupportingDataCourse");
        coursePage = new CoursePage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
        dcmAdminPage = new DCMAdminPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    public void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        coursePage.setCurrentTestReportNode(getCurrentTestReportNode());
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        dcmAdminPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(LoginData.getLoginData(), loginPage);
        dcmAdminPage.isDcmAdminElementPresent();
        dcmAdminPage.clickDcmAdmin();
        coursePage.clickSupportingData();
    }
    @Test(testName = "Create New Course", dataProvider = CourseData.COURSE_DATA_SHEET, dataProviderClass = CourseData.class, priority = 1)
    public void createNewCourse(String courseName,String courseNumber,String sourceId,String jurisdiction) {
        try {
            String randomlyComponentName = coursePage.randomName(courseName,"DCM_Course_");
            coursePage.selectSearchCourse();
            coursePage.clickNewCourse();
            coursePage.enterCourseName(randomlyComponentName);
            coursePage.enterCourseNumber(courseNumber);
            coursePage.clickAddASource();
            coursePage.enterSourceId(sourceId);
            coursePage.selectJurisdiction(jurisdiction);
            coursePage.clickValidateButton();
            String actualSuccessMsg = coursePage.retrieveToastMessage();
            Assert.assertEquals(actualSuccessMsg, coursePage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
            coursePage.clickSaveButton();
        } catch (Exception e) {
            fail("client creation failed due to exception: " + e.getMessage());
        }
    }
    @Test(testName = "Search Course", dataProvider = CourseData.COURSESEARCH_DATA_SHEET, dataProviderClass = CourseData.class, priority = 2)
    public void searchCourse(String searchCourse) throws InterruptedException {
        coursePage.enterSearchTextbox(searchCourse);
        coursePage.clickSearchButton();
        boolean isVisible = coursePage.isValidationCourseNameDisplayed(searchCourse);
        Assert.assertTrue(isVisible, "Search Course Name is not displayed");

    }
    @Test(testName = "Edit Course", dataProvider = CourseData.COURSEEDITED_DATA_SHEET, dataProviderClass = CourseData.class, priority = 3)
    public void editCourse(String newCourseNumber) throws InterruptedException {
        coursePage.clickEditCourse(newCourseNumber);
        coursePage.clickValidateButton();
        String actualSuccessMsg = coursePage.retrieveToastMessage();
        Assert.assertEquals(actualSuccessMsg, coursePage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
        coursePage.clickSaveButton();
        boolean isVisible = coursePage.isValidationCourseNumberDisplayed(newCourseNumber);
        Assert.assertTrue(isVisible, "Search Course Number is not displayed");
    }



}
