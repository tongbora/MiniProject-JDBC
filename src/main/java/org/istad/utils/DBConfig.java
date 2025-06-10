package org.istad.utils;

import java.io.InputStream;
import java.util.Properties;

public class DBConfig {

    private static Properties properties = new Properties();

    static {
        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new RuntimeException("Cannot find app.properties file");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load app.properties", e);
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}

