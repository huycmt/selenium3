package org.example.config;

import org.example.utils.JsonUtils;

public class ConfigLoader {

    public static Configuration loadConfig(String jsonPath) {
        return JsonUtils.fromJson(JsonUtils.getJson(jsonPath), Configuration.class);
    }
}
