/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of ContentStack, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.tests.party;

import com.trilogy.dcm.pages.party.PartyPage;
import com.trilogy.dcm.tests.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

@Listeners({TestListenerAdapter.class})
@Slf4j
public class PartyTest extends BaseTest {

    private final PartyPage partyPage;


    public PartyTest() {
        super("Party");
        partyPage = new PartyPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setCurrentTestReportNode(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
        partyPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @Test(testName = "Party Search", priority = 1)
    public void searchParty() {
        try {
            partyPage.clickParty();
        } catch (Exception e) {
            fail(e);
        }
    }

}
