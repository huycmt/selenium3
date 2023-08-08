package org.example.utils;

import com.codeborne.selenide.SelenideDriver;
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
    static SelenideDriver driver = DriverManager.driver().getDriver();
    static SelenideWait wait = new SelenideWait(driver.getWebDriver(), driver.config().timeout(), driver.config().pollingInterval());

    public static void scrollDownToTheEnd() {
        driver.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        waitForJSandJQueryToLoad();
    }

    public static void scrollUpToTheTop() {
        driver.executeJavaScript("window.scrollTo(0, 0)");
        waitForJSandJQueryToLoad();
    }

    /**
     * Switch to page with window handle
     */
    public static void switchToPage(String page) {
        driver.switchTo().window(page);
        switchToMain();
    }

    /**
     * Switch to page with page number
     */
    public static void switchToPage(int page) {
        driver.switchTo().window(page);
        switchToMain();
    }

    /**
     * Switch to last open page
     */
    public static void switchToLastOpenPage() {
        WebUtils.switchToPage(driver.driver().getWebDriver().getWindowHandles().size() - 1);
    }

    /**
     * Switch to main
     */
    public static void switchToMain() {
        driver.switchTo().defaultContent();
    }

    /**
     * Switch to frame
     */
    public static void switchToFrame(Element frame) {
        driver.switchTo().frame(frame.element().toWebElement());
    }

    public static void waitForTitleContain(String title) {
        wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Close page by window handle
     */
    public static void closePage(String page) {
        driver.driver().switchTo().window(page).close();
    }

    /**
     * Wait for page load by JavaScript
     */
    public static void waitForPageLoad() {
        try {
            wait.until(driver -> {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                return (Boolean) (executor
                        .executeScript("return document.readyState === 'complete';"));
            });

        } catch (Exception e) {
            log.debug("An error occurred when waitForPageLoad - " + e.getMessage());
        }
    }

    public static boolean waitForJSandJQueryToLoad() {
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
        return driver.getWebDriver().getWindowHandle();
    }

    public static String getTitle() {
        return driver.getWebDriver().getTitle();
    }

    /**
     * Get set of window handles
     */
    public static Set<String> getWindowHandles() {
        Set<String> tabs = driver.getWebDriver().getWindowHandles();
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
