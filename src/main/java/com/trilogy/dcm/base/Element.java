/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.base;

import com.aventstack.extentreports.ExtentTest;
import lombok.Getter;

public abstract class Element<D, E> {

    protected final WebDriver<D> webDriver;

    protected final ExtentTest etTest;

    @Getter
    protected final E element;

    protected Element(WebDriver<D> webDriver, ExtentTest etTest, E element) {
        this.webDriver = webDriver;
        this.etTest = etTest;
        this.element = element;
    }

    public Element(WebDriver<D> webDriver, ExtentTest etTest, String xpath, ExpectedConditions expConditions, int timeOut) {
        this.webDriver = webDriver;
        this.etTest = etTest;
        this.element = detectElement(xpath, expConditions, timeOut);
    }

    public abstract E detectElement(String xpath, ExpectedConditions expConditions, int timeOut);

    public abstract String getInnerText();

    public abstract String getText();

    public abstract void setText(String text);

    public abstract void clickElement(String logMessage);

    public abstract void click(String logMessage);

    public abstract boolean isDisplayed();

    public abstract String getCssValue(String propertyName);

    public abstract boolean isEnabled();

    public abstract String getAttribute(String name);

    public abstract boolean isSelected();

    public abstract void hover();

    public abstract void upload(String filePath);

    public abstract void scrollIntoView();

    public abstract void clearText();

    public abstract void scrollFromLeftToRight();

    public abstract void hover(int xOffSet, int yOffSet);

    public abstract void scrollIntoRight();

    public abstract void clickElementWithJS();

    public abstract void sendKeysByPassingKeyboardShortcutKeys(String shortcutKeys);

    public enum ExpectedConditions {
        ELEMENT_TO_BE_CLICKABLE, PRESENCE_OF_ELEMENT_LOCATED, VISIBILITY_OF_ALL_ELEMENTS_LOCATED, INVISIBILITY_OF_ELEMENT_LOCATED;
    }
    public enum WaitType {
        fluent, explicit,
    }
}