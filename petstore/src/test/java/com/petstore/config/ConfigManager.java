package com.petstore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private final Properties props = new Properties();

    private ConfigManager() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) throw new RuntimeException("config.properties not found in classpath");
            props.load(is);
            log.info("Config loaded successfully");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String get(String key) {
        String value = System.getProperty(key, props.getProperty(key));
        if (value == null) throw new RuntimeException("Missing config key: " + key);
        return value.trim();
    }

    public String getBaseUrl()          { return get("base.url"); }
    public String getApiKey()           { return get("api.key"); }
    public String getApiKeyHeader()     { return get("api.key.header"); }
    public long   getResponseTimeout()  { return Long.parseLong(get("response.timeout.ms")); }
}
