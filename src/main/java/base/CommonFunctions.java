package base;

import java.util.Properties;

public class CommonFunctions {
    public static Properties readPropertiesFile() {
        Properties properties = new Properties();
        try {
            properties.load(CommonFunctions.class.getClassLoader().getResourceAsStream("configuration.properties"));
        } catch (Exception e) {
        }
        return properties;
    }
}
