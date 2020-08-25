package amazon_price_notifier.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JDBCProperty {

    public static String get(String name) {
        return readPropertiesFile().getProperty(name);
    }

    public static Properties readPropertiesFile() {
        Properties properties = new Properties();
        String jdbc = "src/main/resources/jdbc.properties";

        try (FileInputStream file = new FileInputStream(jdbc)) {
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
