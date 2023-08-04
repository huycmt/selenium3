package org.example.utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import org.example.driver.Driver;
import org.example.driver.DriverManager;
import org.example.element.Element;

public class WebUtils {

    public static void scrollTillEndOfThePage() {
        driver.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Switch to Page
     *
     * @param page
     */
    public static void switchToPage(String page) {
        driver.switchTo().window(page);
        switchToMain();
    }

    /**
     * Switch to main
     */
    public static void switchToMain() {
        driver.switchTo().defaultContent();
    }

    /**
     * Switch to frame
     *
     * @param frame
     */
    public static void switchToFrame(Element frame) {
        driver.switchTo().frame(frame.element().toWebElement());
    }

    /**
     * Close window by window handle
     *
     * @param window
     */
    public static void closeWindow(String window) {
        switchToPage(window);
    }

    static SelenideDriver driver = DriverManager.driver().getDriver();
}
