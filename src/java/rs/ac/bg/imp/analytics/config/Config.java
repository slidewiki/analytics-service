package rs.ac.bg.imp.analytics.config;

import java.util.Properties;

/**
 *
 * @author Dejan
 */
public class Config {

    private Properties configFile;

    private static Config instance;

    private Config() {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().getResourceAsStream("rs/ac/bg/imp/analytics/config/config.cfg"));
        } catch (Exception eta) {
            eta.printStackTrace();
        }
    }

    private String getValue(String key) {
        return configFile.getProperty(key);
    }

    public static String getProperty(String key) {
        if (instance == null) instance = new Config();
        return instance.getValue(key);
    }
}