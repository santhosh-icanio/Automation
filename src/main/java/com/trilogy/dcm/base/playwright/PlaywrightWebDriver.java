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
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.trilogy.dcm.ConfigManager;
import com.trilogy.dcm.base.Element;
import com.trilogy.dcm.base.WebDriver;
import com.trilogy.dcm.exception.DCMException;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PlaywrightWebDriver extends WebDriver<PlaywrightWebDriver> {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();
    private static final ThreadLocal<FrameLocator> frameLocator = new ThreadLocal<>();

    protected PlaywrightWebDriver() {
        super();
        initializePlaywright();
        this.driver = this;
    }

    public static PlaywrightWebDriver init() {
        PlaywrightWebDriver newDriver = new PlaywrightWebDriver();
        log.info("Running browser version: {}", Optional.ofNullable(browser.get()).map(Browser::version).orElse("unknown"));
        return newDriver;
    }

    private static void setPlaywright() {
        playwright.set(Playwright.create());
    }

    private static void setBrowser() {
        String binary = ConfigManager.getConfig().getPlaywrightBinary();
        String channel = ConfigManager.getConfig().getPlaywrightChannel();
        boolean headless = Boolean.parseBoolean(ConfigManager.getConfig().getPlaywrightIsHeadless());
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setChannel(channel)
                .setHeadless(headless);
                
        Playwright pw = getPlaywright();
        if (pw == null) {
            throw new IllegalStateException("Playwright instance is null");
        }
        
        switch (binary.toLowerCase()) {
            case "webkit":
                browser.set(pw.webkit().launch(launchOptions));
                break;
            case "firefox":
                browser.set(pw.firefox().launch(launchOptions));
                break;
            default:
                launchOptions.setArgs(List.of("--start-maximized"));
                browser.set(pw.chromium().launch(launchOptions));
                break;
        }
    }

    private static void setBrowserContext() {
        Browser br = getBrowser();
        if (br == null) {
            throw new IllegalStateException("Browser instance is null");
        }
        browserContext.set(br.newContext(new Browser.NewContextOptions().setViewportSize(null)));
    }

    private static void setPage() {
        BrowserContext ctx = getBrowserContext();
        if (ctx == null) {
            throw new IllegalStateException("BrowserContext instance is null");
        }
        page.set(ctx.newPage());
    }

    private static Playwright getPlaywright() {
        return playwright.get();
    }

    public static Browser getBrowser() {
        return browser.get();
    }

    private static BrowserContext getBrowserContext() {
        return browserContext.get();
    }

    private static Page getPageInstance() {
        if (page.get() == null) {
            setPage();
        }
        return page.get();
    }

    private void initializePlaywright() {
        if (playwright.get() == null) {
            try {
                setPlaywright();
                setBrowser();
                setBrowserContext();
                setPage();
            } catch (Exception e) {
                log.error("Failed to initialize Playwright: ", e);
                throw new DCMException("Failed to initialize Playwright", e);
            }
        }
    }

    private Page getPage() {
        Page currentPage = getPageInstance();
        if (currentPage == null) {
            throw new IllegalStateException("Page instance is null");
        }
        return currentPage;
    }

    @Override
    public void navigateURL(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        getPage().navigate(url);
    }

    @Override
    public Element<?, ?> getElement(ExtentTest etTest, String xpath, Element.ExpectedConditions expConditions, int timeOut) {
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException("XPath cannot be null or empty");
        }
        return new PlaywrightElement(this, etTest, xpath, expConditions, timeOut);
    }

    @Override
    public List<Element<?, ?>> getElements(ExtentTest etTest, String xpath, int timeOut) {
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException("XPath cannot be null or empty");
        }
        return PlaywrightElement.getElements(this, etTest, xpath, timeOut);
    }

    @Override
    public boolean isElementPresent(String locator) {
        if (locator == null || locator.isEmpty()) {
            return false;
        }
        return PlaywrightElement.isElementPresent(this, locator, Integer.parseInt(ConfigManager.getConfig().getDefaultTimeOut()));
    }

    @Override
    public void refresh() {
        getPage().reload();
    }

    @Override
    public void tearDown() {
        try {
            if (browserContext.get() != null) {
                browserContext.get().close();
            }
            if (browser.get() != null) {
                browser.get().close();
            }
            if (playwright.get() != null) {
                playwright.get().close();
            }
        } catch (Exception e) {
            log.warn("Exception during teardown: ", e);
        } finally {
            frameLocator.remove();
            page.remove();
            browserContext.remove();
            browser.remove();
            playwright.remove();
        }
    }

    @Override
    public void switchToFrames(String... selectors) {
        if (selectors == null || selectors.length == 0) {
            throw new IllegalArgumentException("Selectors cannot be null or empty");
        }
        
        FrameLocator temp = null;
        for (String selector : selectors) {
            if (selector == null || selector.isEmpty()) {
                continue;
            }
            
            if (temp == null) {
                temp = getPage().frameLocator(selector);
            } else {
                temp = temp.frameLocator(selector);
            }
        }
        frameLocator.set(temp);
    }

    public void switchToDefaultFrame() {
        frameLocator.remove();
    }

    @Override
    public String getCurrentUrl() {
        return getPage().url();
    }

    public Locator getByLocator(String selector) {
        if (selector == null || selector.isEmpty()) {
            throw new IllegalArgumentException("Selector cannot be null or empty");
        }
        
        FrameLocator frame = frameLocator.get();
        if (frame != null) {
            return frame.locator(selector);
        }
        return getPage().locator(selector);
    }

    @Override
    public void waitForLoad() {
        getPage().waitForLoadState(LoadState.NETWORKIDLE);
    }

    @Override
    public void waitForLoad(LoadType loadType) {
        if (loadType == null) {
            throw new IllegalArgumentException("LoadType cannot be null");
        }
        try {
            getPage().waitForLoadState(LoadState.valueOf(loadType.name()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid LoadType specified: {}", loadType);
            throw e;
        }
    }

    @Override
    public void sendKeysByPassingKeyboardShortcutKeys(String shortcutKey) {
        if (shortcutKey == null || shortcutKey.isEmpty()) {
            throw new IllegalArgumentException("Shortcut key cannot be null or empty");
        }
        getPage().keyboard().press(shortcutKey);
    }

    @Override
    public Object evaluate(String expression, Object element) {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException("JavaScript expression cannot be null or empty");
        }
        return getPage().evaluate(expression, element);
    }

    @Override
    public void captureScreenShot(Path screenshotPath) {
        if (screenshotPath == null) {
            throw new IllegalArgumentException("Screenshot path cannot be null");
        }
        getPage().screenshot(new Page.ScreenshotOptions()
                .setPath(screenshotPath)
                .setFullPage(true));
    }
}
