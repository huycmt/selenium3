package org.example.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigResourceBundle {

    public static final ConfigResourceBundle CONFIG_RESOURCE = getInstance(ShareParameter.LANGUAGE);
    public static final Locale LOCALE = getLocate(ShareParameter.LANGUAGE);
    private final ResourceBundle resourceConfig;

    private ConfigResourceBundle(String language) {
        resourceConfig = ResourceBundle.getBundle("language", getLocate(language));
    }

    public static Locale getLocate(String language) {
        if ("vi".equals(language)) {
            return new Locale("vi", "VI");
        } else {
            return Locale.ENGLISH;
        }
    }

    public static ConfigResourceBundle getInstance(String language) {
        return new ConfigResourceBundle(language);
    }

    public String getValue(String key) {
        return resourceConfig.getString(key);
    }
}