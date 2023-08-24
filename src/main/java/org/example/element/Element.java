package org.example.element;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SelenideWait;
import org.example.driver.Driver;
import org.example.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;


public class Element {

    private static final Logger log = LoggerFactory.getLogger(Element.class);
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
            case "text":
                return By.xpath(String.format("//*[contains(text(), '%s')]", locatorValue));
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

    public Element scrollIntoView(String s) {
        if (!Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by).scrollIntoView(s);
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

    public Element enter(String text, boolean clear) {
        if (clear) element().clear();
        element().setValue(text);
        return this;
    }

    public Element click() {
        waitForClickable();
        element().click();
        return this;
    }

    public Element uploadFile(File file) {
        element().uploadFile(file);
        return this;
    }

    public Element rightClick() {
        element().contextClick();
        return this;
    }

    public String getText() {
        return element().text();
    }

    public String getAttribute(String name) {
        return element().attr(name);
    }

    public SelenideElement waitForExist() {
        return waitForExist(timeout());
    }

    public SelenideElement waitForExist(Duration timeOut) {
        if (Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by).should(exist, timeOut);
        return this.element;
    }

    public SelenideElement waitForVisible() {
        return waitForExist(timeout());
    }

    public SelenideElement waitForVisible(Duration timeOut) {
        if (Objects.nonNull(this.by)) {
            this.by = by();
        }
        this.element = driver().getDriver().$(this.by).should(visible, timeOut);
        return this.element;
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

    public boolean exists() {
        try {
            return waitForExist() != null;
        } catch (NoSuchElementException | TimeoutException ex) {
            return false;
        }
    }

    public boolean exists(Duration duration) {
        try {
            return waitForExist(duration) != null;
        } catch (NoSuchElementException | TimeoutException ex) {
            return false;
        }
    }

    public boolean isDisplayed() {
        return isDisplayed(timeout());
    }

    public boolean isDisplayed(Duration duration) {
        try {
            return Wait(duration).until(d -> element().isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public Element check() {
        element().setSelected(true);
        return this;
    }

    protected Duration timeout() {
        return Duration.ofMillis(driver().getDriver().config().timeout());
    }

    public SelenideWait Wait(Duration timeout) {
        log.debug("Time out is {} milliseconds", timeout.toMillis());
        SelenideWait wait = new SelenideWait(driver().getDriver().getWebDriver(), timeout.getSeconds(), driver().getDriver().config().pollingInterval());
        return wait;
    }
}
