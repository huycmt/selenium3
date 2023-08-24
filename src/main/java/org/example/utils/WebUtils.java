package org.example.utils;

import com.codeborne.selenide.SelenideWait;
import org.example.driver.DriverManager;
import org.example.element.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class WebUtils {

    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static void scrollDownToTheEnd() {
        scrollTo("0", "document.body.scrollHeight");
    }

    public static void scrollUpToTheTop() {
        scrollTo("0", "0");
    }

    public static void scrollTo(String x, String y) {
        DriverManager.driver().getDriver().executeJavaScript(String.format("window.scrollTo(%s, %s)", x, y));
        waitForJSandJQueryToLoad();
    }

    public static void scrollBy(int x, int y) {
        DriverManager.driver().getDriver().executeJavaScript(String.format("window.scrollBy(%d, %d)", x, y));
        waitForJSandJQueryToLoad();
    }

    /**
     * Switch to page with window handle
     */
    public static void switchToPage(String page) {
        DriverManager.driver().getDriver().switchTo().window(page);
        switchToMain();
    }

    /**
     * Switch to page with page number
     */
    public static void switchToPage(int page) {
        DriverManager.driver().getDriver().switchTo().window(page);
        switchToMain();
    }

    /**
     * Switch to last open page
     */
    public static void switchToLastOpenPage() {
        WebUtils.switchToPage(DriverManager.driver().getDriver().getWebDriver().getWindowHandles().size() - 1);
    }

    /**
     * Switch to main
     */
    public static void switchToMain() {
        DriverManager.driver().getDriver().switchTo().defaultContent();
    }

    /**
     * Switch to frame
     */
    public static void switchToFrame(Element frame) {
        DriverManager.driver().getDriver().switchTo().frame(frame.element().toWebElement());
    }

    public static void waitForTitleContain(String title) {
        SelenideWait wait = new SelenideWait(DriverManager.driver().getDriver().getWebDriver(), DriverManager.driver().getDriver().config().timeout(), DriverManager.driver().getDriver().config().pollingInterval());
        wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Close page by window handle
     */
    public static void closePage(String page) {
        DriverManager.driver().getDriver().switchTo().window(page).close();
    }

    /**
     * Wait for page load by JavaScript
     */
    public static void waitForPageLoad() {
        try {
            SelenideWait wait = new SelenideWait(DriverManager.driver().getDriver().getWebDriver(), DriverManager.driver().getDriver().config().timeout(), DriverManager.driver().getDriver().config().pollingInterval());
            wait.until(driver -> {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                return (Boolean) (executor
                        .executeScript("return document.readyState === 'complete';"));
            });
        } catch (Exception e) {
            log.debug("An error occurred when waitForPageLoad - " + e.getMessage());
        }
    }

    /**
     * Wait for page load by JavaScript and JQuery
     */
    public static boolean waitForJSandJQueryToLoad() {
        SelenideWait wait = new SelenideWait(DriverManager.driver().getDriver().getWebDriver(), DriverManager.driver().getDriver().config().timeout(), DriverManager.driver().getDriver().config().pollingInterval());
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
            } catch (Exception e) {
                // no jQuery present
                return true;
            }
        };
        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .toString().equals("complete");
        pause(2000);
        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    /**
     * Get current window handle ID
     */
    public static String getWindowHandle() {
        return DriverManager.driver().getDriver().getWebDriver().getWindowHandle();
    }

    public static String getTitle() {
        return DriverManager.driver().getDriver().getWebDriver().getTitle();
    }

    /**
     * Get set of window handles
     */
    public static Set<String> getWindowHandles() {
        Set<String> tabs = DriverManager.driver().getDriver().getWebDriver().getWindowHandles();
        return tabs;
    }


    public static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
