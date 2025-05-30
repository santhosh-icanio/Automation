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
import com.trilogy.dcm.exception.ElementNotVisibleException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.List;

import static com.trilogy.dcm.pages.BasePage.MAX_RETRY_ATTEMPTS;

@Slf4j
@Getter
public abstract class WebDriver<T> {

    protected T driver;

    protected WebDriver() {
    }

    public abstract void navigateURL(String url);

    public abstract void tearDown();

    public abstract Element<?, ?> getElement(ExtentTest etTest, String xpath, Element.ExpectedConditions expConditions, int timeOut);

    public abstract List<Element<?, ?>> getElements(ExtentTest etTest, String xpath, int timeOut);

    public abstract boolean isElementPresent(String locator);

    public void clickElement(ExtentTest currentTestReportNode, String xpath, Element.ExpectedConditions expConditions, int timeout, String field) {
        Element<?, ?> element = getElement(currentTestReportNode, xpath,
                expConditions,
                timeout
        );
        clickElement(element, field);
    }

    public void clickElement(Element<?, ?> element, String field) {
        element.clickElement(String.format("%s button is clicked successfully", field));
    }

    public boolean setTextElement(ExtentTest currentTestReportNode,
                                  String xpath,
                                  Element.ExpectedConditions expectedConditions,
                                  int timeout,
                                  String text,
                                  String fieldName) {
        Element<?, ?> element = getElement(currentTestReportNode, xpath,
                expectedConditions,
                timeout
        );
        return setTextElement(element, text, fieldName);
    }

    /**
     * Sets text on the specified element with retry mechanism.
     *
     * @param element   the target element to set text on
     * @param text      the text to be set
     * @param fieldName the name of the field for logging purposes
     * @return true if text was set successfully, false otherwise
     */
    public boolean setTextElement(Element<?, ?> element, String text, String fieldName) {
        for (int attempt = 0; attempt < MAX_RETRY_ATTEMPTS; attempt++) {
            try {
                validateElementVisibility(element, fieldName);
                element.setText(text);
                log.debug("Successfully set text '{}' on element {}", text, fieldName);
                return true;
            } catch (ElementNotVisibleException e) {
                log.warn("Attempt {} of {}: Retrying to set text on element {}, error: {}",
                        attempt + 1, MAX_RETRY_ATTEMPTS, fieldName, e.getMessage());
            } catch (Exception e) {
                log.error("Failed to set text on element {}: {}", fieldName, e.getMessage());
                return false;
            }
        }
        return false;
    }

    private void validateElementVisibility(Element<?, ?> element, String fieldName) {
        if (!element.isDisplayed()) {
            throw new ElementNotVisibleException(
                    String.format("Field '%s' is not displayed after %d attempts", fieldName, MAX_RETRY_ATTEMPTS));
        }
    }


    public String getText(ExtentTest currentTestReportNode,
                          String xpath,
                          Element.ExpectedConditions expectedConditions,
                          int timeout) {
        for (int attempt = 0; attempt < MAX_RETRY_ATTEMPTS; attempt++) {
            try {
                Element<?, ?> element = getElement(
                        currentTestReportNode,
                        xpath,
                        expectedConditions,
                        timeout
                );

                if (element == null) {
                    log.error("Element not found for xpath: {}", xpath);
                    throw new ElementNotVisibleException(
                            String.format("Element not found using selector: %s", xpath)
                    );
                }

                return element.getText();

            } catch (ElementNotVisibleException e) {
                log.info("Attempt {}/{}: Stale element reference encountered, retrying...",
                        attempt + 1, MAX_RETRY_ATTEMPTS, e);
                if (attempt == MAX_RETRY_ATTEMPTS - 1) {
                    log.error("Failed to retrieve text after {} attempts", MAX_RETRY_ATTEMPTS);
                    return null;
                }
            } catch (Exception e) {
                log.error("Unexpected error while getting element text: {}", e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

    public boolean isEnabled(ExtentTest currentTestReportNode,
                             String xpath,
                             Element.ExpectedConditions expectedConditions,
                             int timeout) {
        return getElement(currentTestReportNode, xpath,
                expectedConditions,
                timeout).isEnabled();
    }

    public abstract void refresh();

    public abstract void switchToFrames(String... selectors);

    public abstract void switchToDefaultFrame();

    public abstract String getCurrentUrl();

    public abstract void waitForLoad();

    public abstract void waitForLoad(LoadType loadType);

    public abstract void sendKeysByPassingKeyboardShortcutKeys(String shortcutKey);

    public abstract Object evaluate(String expression, Object element);

    public abstract void captureScreenShot(Path screenshotPath);

    public enum LoadType {
        LOAD,
        DOMCONTENTLOADED,
        NETWORKIDLE
    }

}