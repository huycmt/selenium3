package utils;

import lombok.Data;
import org.openqa.selenium.MutableCapabilities;

@Data
public class Configuration {

    private String browser;
    private boolean headless;
    private String remote;
    private String browserSize;
    private String browserVersion;
    private boolean startMaximized;
    private String pageLoadStrategy;
    private MutableCapabilities browserCapabilities;
    private String baseUrl;
    private long timeout;
    private long pollingInterval;
    private boolean clickViaJs;
}
