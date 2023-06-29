package org.example.config;

import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.SelenideConfig;
import lombok.Data;
import org.example.utils.JsonUtils;
import org.openqa.selenium.MutableCapabilities;

import java.util.HashMap;
import java.util.Map;

@Data
public class Configuration {

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
    private long pollingInterval;
    private boolean clickViaJs;

    public Configuration() {
        this.startMaximized = true;
        this.browser = "chrome";
    }

    public SelenideConfig toSelenideConfig(){
        return JsonUtils.fromJson(JsonUtils.toJson(this), SelenideConfig.class);
    }
}
