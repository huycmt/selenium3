package org.example.element;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.driver.Driver;
import org.example.driver.DriverManager;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;


public class Element {

    private String locator;
    private String dynamicLocator;
    private By by;
    private SelenideElement element;
    private boolean alwaysFind;

    public Element(SelenideElement element) {
        this.element = element;
    }

    public Element(SelenideElement element, int index) {
        this.element = element;
    }

    public Element(String locator) {
        this.locator = locator;
        this.dynamicLocator = locator;
        this.by = by();
    }

    public Element(String locator, boolean alwaysFind) {
        this(locator);
        this.alwaysFind = alwaysFind;
    }

    private By by() {
        String locatorType = this.locator.split("=")[0];
        String locatorValue = this.locator.substring(locatorType.length() + 1);

        switch (locatorType) {
            case "css":
                return By.cssSelector(locatorValue);
            case "id":
                return By.id(locatorValue);
            case "link":
                return By.linkText(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "className":
                return By.className(locatorValue);
            default:
                return By.xpath(locator);
        }
    }

    public void set(Object... args) {
        this.element = null;
        this.locator = String.format(this.dynamicLocator, args);
        this.by = by();
    }

    public SelenideElement element() {
        if (Objects.nonNull(element) && !alwaysFind) {
            return this.element;
        }

        if (!Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by);
        return element;
    }

    public SelenideElement element(int index) {
        if (Objects.nonNull(element) && !alwaysFind) {
            return this.element;
        }

        if (!Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by, index);
        return element;
    }

    public Element scrollIntoView(boolean b) {
        if (!Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by).scrollIntoView(b);
        return this;
    }

    public Element get(int index) {
        return new Element(element(index));
    }

    private Driver driver() {
        return DriverManager.driver();
    }

    public Element enter(String text) {
        element().setValue(text);
        return this;
    }

    public Element click() {
        element().click();
        return this;
    }

    public void waitForExist() {
        waitForExist(timeout());
    }

    public void waitForExist(Duration timeOut) {
        if (Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by).should(exist, timeOut);
    }

    public void waitForInvisible(Duration timeOut) {
        if (Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by).should(disappear, timeOut);
    }

    public void waitForInvisible() {
        waitForInvisible(timeout());
    }


    public void waitForClickable(Duration timeOut) {
        if (Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by).should(interactable, timeOut);
    }

    public void waitForClickable() {
        waitForClickable(timeout());
    }

    public ElementsCollection elements() {
        if (!Objects.nonNull(this.by)) {
            this.by = by();
        }
        return driver().getDriver().$$(this.by);
    }

    public boolean isDisplayed() {
        return element().isDisplayed();
    }

    public Element check() {
        element().setSelected(true);
        return this;
    }

    protected Duration timeout() {
        return Duration.ofMillis(driver().getDriver().config().timeout());
    }

}
