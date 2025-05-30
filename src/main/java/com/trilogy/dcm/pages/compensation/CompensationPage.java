package com.trilogy.dcm.pages.compensation;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class CompensationPage extends BasePage {

    private static final String LOCATOR_COMPENSATION_BUTTON = "//ul[@id=\"navigation\"]//span[text()=\"Compensation\"]";
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";
    public CompensationPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickCompensation() {
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        clickElement(LOCATOR_COMPENSATION_BUTTON, "Compensation");
        getWebDriver().waitForLoad();
    }
    public void isCompensationElementPresent(){
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        isElementPresent(LOCATOR_COMPENSATION_BUTTON);
    }
}
