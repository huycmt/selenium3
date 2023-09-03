package org.example.driver;

import org.example.config.Configuration;

import java.util.HashMap;

public class DriverManager {

    public DriverManager() {
        Configuration config = new Configuration();
        configs.put(getCurrentThreadID(), config);
    }

    public static Driver driver() {
        return getCurrentDriver();
    }

    public static Configuration config() {
        return getCurrentConfig();
    }

    public void open(String url) {
        initDriver();
        driver().open(url);
    }

    public void open() {
        initDriver();
        driver().open();
    }

    public void initDriver() {
        long currentThreadID = getCurrentThreadID();
        if (!drivers.containsKey(currentThreadID)) {
            Driver driver = new Driver(configs.get(currentThreadID));
            synchronized (driver) {
                drivers.put(currentThreadID, driver);
            }
        }
    }

    public void useConfig(Configuration config) {
        configs.put(getCurrentThreadID(), config);
    }

    public void close() {
        if (drivers.size() > 0) {
            long threadID = getCurrentThreadID();
            getDriver(threadID).close();
            drivers.remove(threadID);
            configs.remove(threadID);
        }
    }
    private static final HashMap<Long, Driver> drivers = new HashMap<>();
    private static final HashMap<Long, Configuration> configs = new HashMap<>();

    private static Driver getCurrentDriver() {
        return getDriver(getCurrentThreadID());
    }

    private static Driver getDriver(long threadID) {
        return drivers.get(threadID);
    }

    private static long getCurrentThreadID() {
        return Thread.currentThread().getId();
    }

    private static Configuration getCurrentConfig() {
        return getConfig(getCurrentThreadID());
    }

    private static Configuration getConfig(long threadID) {
        return configs.get(threadID);
    }
}
