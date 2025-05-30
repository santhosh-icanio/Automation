package com.trilogy.dcm.pages.dcmAdmin;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class DCMAdminPage extends BasePage {
    private static final String LOCATOR_DCMADMIN_BUTTON = "//ul[@id=\"navigation\"]//span[text()=\"DCM Admin\"]";
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";
    public DCMAdminPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickDcmAdmin() {
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        clickElement(LOCATOR_DCMADMIN_BUTTON, "Dcm Admin");
        getWebDriver().waitForLoad();
    }
    public void isDcmAdminElementPresent(){
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        isElementPresent(LOCATOR_DCMADMIN_BUTTON);
    }
}

