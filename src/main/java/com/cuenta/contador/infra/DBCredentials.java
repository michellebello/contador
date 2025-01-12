package com.cuenta.contador.infra;

import java.io.FileInputStream;
import java.util.Properties;

public class DBCredentials {
    private static final String DEFAULT_FILENAME = "config.properties";

    private final String user;
    private final String password;
    private final String url;
    private final String driver;

    public static DBCredentials fromDefaultFile() {
        return DBCredentials.fromFile(DEFAULT_FILENAME);
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    private DBCredentials(String user, String password, String url, String driver) {
        this.user = user;
        this.password = password;
        this.url = url;
        this.driver = driver;
    }

    private static DBCredentials fromFile(String filename) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            Properties prop = new Properties();
            prop.load(fis);

            String user = prop.getProperty("jdbc.user");
            String password = prop.getProperty("jdbc.password");
            String url = prop.getProperty("jdbc.url");
            String driver = prop.getProperty("jdbc.driver");

            return new DBCredentials(user, password, url, driver);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
