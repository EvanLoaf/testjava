package com.gmail.evanloafakahaitao.config;

import com.gmail.evanloafakahaitao.config.properties.DatabaseProperties;
import com.gmail.evanloafakahaitao.config.properties.FileProperties;
import com.gmail.evanloafakahaitao.config.properties.PageProperties;

import java.util.ResourceBundle;

public class ConfigurationManager {

    private static ConfigurationManager instance;

    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "config";

    public final DatabaseProperties DATABASE_PROPERTIES = new DatabaseProperties();
    public final PageProperties PAGE_PROPERTIES = new PageProperties();
    public final FileProperties FILE_PROPERTIES = new FileProperties();

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}
