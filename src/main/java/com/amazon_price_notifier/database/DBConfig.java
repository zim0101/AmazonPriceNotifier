package com.amazon_price_notifier.database;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBConfig {

    private final static String propertyFile = "src/main/resources/credentials/" +
            "jdbc.properties";

    protected static final HashMap<String, String> defaultDatabase =
            (HashMap<String, String>) Map.of(
                    "source", getJdbcProperty("default_source"),
                    "host", getJdbcProperty("default_host"),
                    "port", getJdbcProperty("default_port"),
                    "database", getJdbcProperty("default_database"),
                    "username", getJdbcProperty("default_username"),
                    "password", getJdbcProperty("default_password")
    );

    protected static final HashMap<String, String> demoDatabase =
            (HashMap<String, String>) Map.of(
                    "source", getJdbcProperty("demo_source"),
                    "host", getJdbcProperty("demo_host"),
                    "port", getJdbcProperty("demo_port"),
                    "database", getJdbcProperty("demo_database"),
                    "username", getJdbcProperty("demo_username"),
                    "password", getJdbcProperty("demo_password")
    );

    public static HashMap<String, String> defaultDatabase() {
        return defaultDatabase;
    }

    public static HashMap<String, String> demoDatabase() {
        return demoDatabase;
    }

    private static String getJdbcProperty(String name) {
        return readPropertiesFile().getProperty(name);
    }

    private static Properties readPropertiesFile() {
        Properties properties = new Properties();
        try (FileInputStream file = new FileInputStream(propertyFile)) {
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
