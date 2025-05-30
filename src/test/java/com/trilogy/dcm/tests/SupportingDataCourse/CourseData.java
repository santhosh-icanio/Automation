package com.trilogy.dcm.tests.SupportingDataCourse;

import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.tests.BaseData;

public class CourseData extends BaseData {
    public static final String COURSE_DATA_SHEET = "course";
    public static final String COURSESEARCH_DATA_SHEET = "search";
    public static final String COURSEEDITED_DATA_SHEET = "edited";

    @org.testng.annotations.DataProvider(name = COURSE_DATA_SHEET)
    public static Object[][] getCourseData() {
        return getTestDataJSON(ConfigManager.getConfig().getCourseFile(),
                COURSE_DATA_SHEET);
    }
    @org.testng.annotations.DataProvider(name = COURSESEARCH_DATA_SHEET)
    public static Object[][] getCourseSearchData() {
        return getTestDataJSON(ConfigManager.getConfig().getCourseFile(),
                COURSESEARCH_DATA_SHEET);
    }
    @org.testng.annotations.DataProvider(name = COURSEEDITED_DATA_SHEET)
    public static Object[][] getCourseEditData() {
        return getTestDataJSON(ConfigManager.getConfig().getCourseFile(),
                COURSEEDITED_DATA_SHEET);
    }
}
