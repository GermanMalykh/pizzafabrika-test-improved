package common_configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Properties properties = new Properties();
    private static final String CONFIG_FILE_NAME = "config.properties";

    private Config() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (input == null) {
                throw new RuntimeException(CONFIG_FILE_NAME + " file not found in resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Fail to load " + CONFIG_FILE_NAME, e);
        }
    }

    public static String getProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null) return systemValue;

        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null) return envValue;

        return  INSTANCE.properties.getProperty(key);
    }
}
