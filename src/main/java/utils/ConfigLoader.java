package utils;

import com.codeborne.selenide.Configuration;

import java.util.Objects;

public class ConfigLoader {

    public static void updateConfig(String jsonPath) {
        utils.Configuration config = JsonUtils.toObject(jsonPath, utils.Configuration.class);

        Configuration.browser = Objects.requireNonNullElse(config.getBrowser(), Configuration.browser);
        Configuration.headless = config.isHeadless();
        Configuration.remote = Objects.nonNull(config.getRemote()) ? config.getRemote() : Configuration.remote;
        Configuration.browserSize = Objects.nonNull(config.getBrowserSize()) ? config.getBrowserSize() : Configuration.browserSize;
        Configuration.browserVersion = Objects.nonNull(config.getBrowserVersion()) ? config.getBrowserVersion() : Configuration.browserVersion;
        Configuration.pageLoadStrategy = Objects.nonNull(config.getPageLoadStrategy()) ? config.getPageLoadStrategy() : Configuration.pageLoadStrategy;
        Configuration.baseUrl = Objects.requireNonNullElse(config.getBaseUrl(), Configuration.baseUrl);
        Configuration.timeout = config.getTimeout();
        Configuration.pollingInterval = config.getPollingInterval();
    }
}
