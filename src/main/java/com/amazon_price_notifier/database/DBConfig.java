package com.amazon_price_notifier.database;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * DBConfig class contains multiple database configurations. To add new
 * configuration declare new final static variable with Map type and return
 * it from another static method.
 */
public class DBConfig {

    /**
     * Location of jdbc properties file.
     */
    // TODO: Remove plain path from codebase. Develop API for that. Its risky.
    private final static String propertyFile = "src/main/resources/credentials/" +
            "jdbc.properties";

    /**
     * Primary Database configuration
     */
    protected static final Map<String, String> primaryDatabase = Map.of(
                    "source", getJdbcProperty("default_source"),
                    "host", getJdbcProperty("default_host"),
                    "port", getJdbcProperty("default_port"),
                    "database", getJdbcProperty("default_database"),
                    "username", getJdbcProperty("default_username"),
                    "password", getJdbcProperty("default_password"));

    /**
     * Demo database configuration
     */
    protected static final Map<String, String> demoDatabase = Map.of(
                    "source", getJdbcProperty("demo_source"),
                    "host", getJdbcProperty("demo_host"),
                    "port", getJdbcProperty("demo_port"),
                    "database", getJdbcProperty("demo_database"),
                    "username", getJdbcProperty("demo_username"),
                    "password", getJdbcProperty("demo_password"));

    /**
     * @return Configuration of primary database
     */
    public static Map<String, String> primary() {
        return primaryDatabase;
    }

    /**
     * @return Configuration of demo database
     */
    public static Map<String, String> demo() {
        return demoDatabase;
    }

    /**
     * Returns jdbc property value by name
     *
     * @param name Jdbc property name
     * @return jdbc property value
     */
    private static String getJdbcProperty(String name) {
        return readPropertiesFile().getProperty(name);
    }

    /**
     * Read jdbc properties file and return all properties
     *
     * @return jdbc properties
     */
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
