package org.example.config;

import com.codeborne.selenide.SelenideConfig;
import lombok.Data;
import org.example.utils.JsonUtils;
import org.openqa.selenium.MutableCapabilities;

@Data
public class Configuration {

    public Configuration() {
        this.startMaximized = true;
        this.browser = "chrome";
    }

    public SelenideConfig toSelenideConfig() {
        SelenideConfig selenideConfig = JsonUtils.fromJson(JsonUtils.toJson(this), SelenideConfig.class);
        return selenideConfig;
    }
    private String browser;
    private boolean headless;
    private String remote;
    private String browserSize;
    private String browserVersion;
    private boolean startMaximized;
    private String pageLoadStrategy;
    private String assertionMode;
    private MutableCapabilities browserCapabilities;
    private String baseUrl;
    private long timeout;
    private long pageLoadTimeout;
    private long pollingInterval;
    private boolean clickViaJs;
    private boolean holdBrowserOpen;
    private String fileDownload;
}
