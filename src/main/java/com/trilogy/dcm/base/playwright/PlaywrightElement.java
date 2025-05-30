/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.base.playwright;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.trilogy.dcm.base.Element;
import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.exception.DCMException;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class PlaywrightElement extends Element<PlaywrightWebDriver, Locator> {

    protected PlaywrightElement(WebDriver<PlaywrightWebDriver> webDriver, ExtentTest etTest, Locator element) {
        super(webDriver, etTest, element);
    }

    public PlaywrightElement(WebDriver<PlaywrightWebDriver> webDriver, ExtentTest etTest, String xpath, Element.ExpectedConditions expConditions, int timeout) {
        super(webDriver, etTest, xpath, expConditions, timeout);
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException("XPath cannot be null or empty");
        }
    }

    /**
     * Detects elements matching the given xpath with timeout
     *
     * @param webDriver The WebDriver instance
     * @param xpath The xpath to locate elements
     * @param timeout The maximum time to wait in milliseconds
     * @return List of Locator objects representing found elements
     * @throws DCMException if detection fails for reasons other than element not found
     */
    private static List<Locator> detectElements(WebDriver<PlaywrightWebDriver> webDriver, String xpath, Element.ExpectedConditions expConditions, int timeout) {
        if (webDriver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException("XPath cannot be null or empty");
        }
        if (timeout < 0) {
            throw new IllegalArgumentException("Timeout cannot be negative");
        }
        
        try {
            Locator locator = webDriver.getDriver().getByLocator(xpath);
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(getWaitForSelectorState(expConditions))
                    .setTimeout(timeout)
            );
            return locator.all();
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw e;
            }
            log.warn("Failed to detect elements with xpath: {}, timeout: {}ms", xpath, timeout, e);
            return Collections.emptyList();
        }
    }

    private static WaitForSelectorState getWaitForSelectorState(ExpectedConditions expConditions) {
        return switch (expConditions) {
            case PRESENCE_OF_ELEMENT_LOCATED -> WaitForSelectorState.ATTACHED;
            case INVISIBILITY_OF_ELEMENT_LOCATED -> WaitForSelectorState.HIDDEN;
            default -> WaitForSelectorState.VISIBLE;
        };
    }

    public static List<Element<?, ?>> getElements(WebDriver<PlaywrightWebDriver> webDriver, ExtentTest etTest, String locator, int timeout) {
        if (webDriver == null || etTest == null) {
            throw new IllegalArgumentException("WebDriver and ExtentTest cannot be null");
        }
        if (locator == null || locator.isEmpty()) {
            throw new IllegalArgumentException("Locator cannot be null or empty");
        }
        
        List<Locator> elements = detectElements(webDriver, locator, ExpectedConditions.VISIBILITY_OF_ALL_ELEMENTS_LOCATED, timeout);
        return elements.stream()
                .filter(Objects::nonNull)
                .map(element -> (Element<?, ?>) new PlaywrightElement(webDriver, etTest, element))
                .collect(Collectors.toList());
    }

    public static boolean isElementPresent(WebDriver<PlaywrightWebDriver> webDriver, String locator, int timeout) {
        if (webDriver == null) {
            return false;
        }
        if (locator == null || locator.isEmpty()) {
            return false;
        }
        
        try {
            List<Locator> elements = detectElements(webDriver, locator, ExpectedConditions.VISIBILITY_OF_ALL_ELEMENTS_LOCATED, timeout);
            return elements.stream().anyMatch(Locator::isVisible);
        } catch (Exception e) {
            log.debug("Exception checking for element presence: {}", locator, e);
            return false;
        }
    }

    @Override
    public Locator detectElement(String xpath, Element.ExpectedConditions expConditions, int timeout) {
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException("XPath cannot be null or empty");
        }
        
        return detectElements(this.webDriver, xpath, expConditions, timeout)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getInnerText() {
        validateElement();
        try {
            return element.innerText();
        } catch (Exception e) {
            log.error("Failed to get inner text", e);
            return "";
        }
    }

    @Override
    public String getText() {
        validateElement();
        try {
            return element.textContent();
        } catch (Exception e) {
            log.error("Failed to get text content", e);
            return "";
        }
    }

    @Override
    public void setText(String text) {
        validateElement();
        if (text == null) {
            text = "";  // Handle null text as empty string
        }
        
        try {
            element.fill(text);
            String elementName = getElementIdentifier();
            log.info("Given text [{}] entered on element [{}], current value [{}]", text, elementName, element.textContent());
            this.etTest.log(Status.INFO, String.format("Given text [%s] entered on element [%s]", text, elementName));
        } catch (Exception e) {
            String message = "Failed to set text: " + text;
            log.error(message, e);
            this.etTest.log(Status.FAIL, message + ": " + e.getMessage());
            throw new DCMException(message, e);
        }
    }

    private String getElementIdentifier() {
        try {
            String name = element.getAttribute("name");
            if (name != null && !name.isEmpty()) {
                return name;
            }
            
            String id = element.getAttribute("id");
            if (id != null && !id.isEmpty()) {
                return id;
            }
            
            return element.toString();
        } catch (Exception e) {
            return "unknown";
        }
    }

    private void performClick(String logMessage) {
        validateElement();
        try {
            element.click();
            webDriver.waitForLoad(WebDriver.LoadType.LOAD);
            webDriver.waitForLoad(WebDriver.LoadType.NETWORKIDLE);
            webDriver.waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
            this.etTest.log(Status.INFO, logMessage);
        } catch (Exception e) {
            String message = "Failed to click element";
            log.error(message, e);
            this.etTest.log(Status.FAIL, message + ": " + e.getMessage());
            throw new DCMException(message, e);
        }
    }

    @Override
    public void clickElement(String logMessage) {
        performClick(logMessage);
    }

    @Override
    public void click(String logMessage) {
        performClick(logMessage);
    }

    @Override
    public boolean isDisplayed() {
        try {
            validateElement();
            return element.isVisible();
        } catch (Exception e) {
            log.debug("Exception checking if element is displayed", e);
            return false;
        }
    }

    @Override
    public boolean isEnabled() {
        try {
            validateElement();
            return element.isEnabled();
        } catch (Exception e) {
            log.debug("Exception checking if element is enabled", e);
            return false;
        }
    }

    @Override
    public boolean isSelected() {
        try {
            validateElement();
            return element.isChecked();
        } catch (Exception e) {
            log.debug("Exception checking if element is selected", e);
            return false;
        }
    }

    @Override
    public void hover() {
        validateElement();
        try {
            element.hover();
        } catch (Exception e) {
            String message = "Failed to hover over the element";
            log.error(message, e);
            throw new DCMException(message, e);
        }
    }

    @Override
    public void hover(int xOffset, int yOffset) {
        validateElement();
        try {
            // Playwright doesn't directly support hover with offset, but we can use evaluate
            // to implement custom hover with offset
            webDriver.evaluate(
                    "([element, x, y]) => { " +
                            "const rect = element.getBoundingClientRect(); " +
                            "const centerX = rect.left + rect.width / 2 + x; " +
                            "const centerY = rect.top + rect.height / 2 + y; " +
                            "const event = new MouseEvent('mouseover', { " +
                            "  bubbles: true, " +
                            "  clientX: centerX, " +
                            "  clientY: centerY " +
                            "}); " +
                            "element.dispatchEvent(event); " +
                            "}", 
                    List.of(element, xOffset, yOffset)
            );
        } catch (Exception e) {
            String message = "Failed to hover with offset";
            log.error(message, e);
            throw new DCMException(message, e);
        }
    }

    @Override
    public void upload(String filePath) {
        validateElement();
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        
        try {
            Path path = Paths.get(filePath);
            element.setInputFiles(path);
            log.info("Uploaded file: {}", filePath);
            this.etTest.log(Status.INFO, "File uploaded: " + filePath);
        } catch (Exception e) {
            String message = "Failed to upload file: " + filePath;
            log.error(message, e);
            this.etTest.log(Status.FAIL, message);
            throw new DCMException(message, e);
        }
    }

    @Override
    public void clickElementWithJS() {
        validateElement();
        try {
            webDriver.evaluate("element => element.click()", element);
            log.info("Clicked element with JavaScript");
        } catch (Exception e) {
            String message = "Failed JavaScript click";
            log.error(message, e);
            this.etTest.log(Status.FAIL, message);
            throw new DCMException(message, e);
        }
    }

    @Override
    public void sendKeysByPassingKeyboardShortcutKeys(String shortcutKey) {
        if (shortcutKey == null || shortcutKey.isEmpty()) {
            throw new IllegalArgumentException("Shortcut key cannot be null or empty");
        }
        webDriver.sendKeysByPassingKeyboardShortcutKeys(shortcutKey);
    }

    @Override
    public String getCssValue(String propertyName) {
        validateElement();
        if (propertyName == null || propertyName.isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be null or empty");
        }
        
        try {
            return (String) webDriver.evaluate(
                    "([element, prop]) => window.getComputedStyle(element).getPropertyValue(prop)",
                    List.of(element, propertyName)
            );
        } catch (Exception e) {
            log.error("Failed to get CSS value for property: {}", propertyName, e);
            return "";
        }
    }

    @Override
    public String getAttribute(String name) {
        validateElement();
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Attribute name cannot be null or empty");
        }
        
        try {
            return element.getAttribute(name);
        } catch (Exception e) {
            log.error("Failed to get attribute: {}", name, e);
            return "";
        }
    }

    /**
     * Scrolls the element into view
     */
    public void scrollIntoView() {
        validateElement();
        try {
            element.scrollIntoViewIfNeeded();
            log.debug("Scrolled element into view");
        } catch (Exception e) {
            String message = "Failed to scroll element into view";
            log.error(message, e);
            throw new DCMException(message, e);
        }
    }

    /**
     * Clears text from the element
     */
    public void clearText() {
        validateElement();
        try {
            element.fill("");
            log.info("Cleared text from element [{}]", getElementIdentifier());
            this.etTest.log(Status.INFO, "Cleared text from element");
        } catch (Exception e) {
            String message = "Failed to clear text";
            log.error(message, e);
            this.etTest.log(Status.FAIL, message);
            throw new DCMException(message, e);
        }
    }

    /**
     * Scroll element horizontally from left to right
     */
    public void scrollFromLeftToRight() {
        validateElement();
        try {
            webDriver.evaluate(
                    "element => { element.scrollLeft = element.scrollWidth; }",
                    element
            );
            log.debug("Scrolled element from left to right");
        } catch (Exception e) {
            String message = "Failed to scroll from left to right";
            log.error(message, e);
            throw new DCMException(message, e);
        }
    }

    /**
     * Scroll element to the rightmost position
     */
    public void scrollIntoRight() {
        scrollFromLeftToRight(); // Same implementation in Playwright
    }
    
    /**
     * Validates that the element is not null
     * @throws DCMException if element is null
     */
    private void validateElement() {
        if (element == null) {
            String message = "Element is null or not found";
            log.error(message);
            throw new DCMException(message);
        }
    }
}
