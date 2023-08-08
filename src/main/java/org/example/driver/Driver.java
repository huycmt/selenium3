package org.example.driver;

import com.codeborne.selenide.SelenideDriver;
import io.qameta.allure.Step;
import lombok.Getter;
import org.example.config.Configuration;

public class Driver {

    @Getter
    private final Configuration config;
    @Getter
    private final SelenideDriver driver;

    public Driver(Configuration config) {
        this.config = config;
        this.driver = new SelenideDriver(config.toSelenideConfig());
    }

    @Step("Navigate to {url}")
    public void open(String url) {
        this.driver.open(url);
        if (config.isStartMaximized()) {
            this.driver.getWebDriver().manage().window().maximize();
        }
    }

    public void open() {
        this.driver.open();
        if (config.isStartMaximized()) {
            this.driver.getWebDriver().manage().window().maximize();
        }
    }

    public void close() {
        this.driver.close();
    }
}
