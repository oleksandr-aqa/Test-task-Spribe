package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static Properties properties = new Properties();

    static {
        try (InputStream inputStream = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("config.properties file not found in resources!");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
