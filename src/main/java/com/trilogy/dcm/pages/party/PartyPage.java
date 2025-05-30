/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.pages.party;

import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.pages.BasePage;

public class PartyPage extends BasePage {
    private static final String LOCATOR_PARTY_BUTTON = "ul.navbar-nav.top-nav li a#Party span";
    private static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";

    public PartyPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickParty() {
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        clickElement(LOCATOR_PARTY_BUTTON, "Party");
        getWebDriver().waitForLoad();
    }

    public void isPartyElementPresent(){
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        isElementPresent(LOCATOR_PARTY_BUTTON);
    }

}
