package ch.tichuana.tichu.client.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private Properties properties;
    private String configPath;

    /**
     * Loads the properties file. Based on the JavaFX-App-Template.
     * @author Christian
     * @param configPath
     */
    public Configuration(String configPath){
        this.properties = new Properties();
        this.configPath = configPath;
        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // return property by key
    public String getProperty(String key){
        return properties.getProperty(key);
    }

    // set a property
    public void setProperty(String key, String value){
        properties.setProperty(key, value);
    }

    // save current properties to file
    public void save(){
        FileOutputStream propertiesFile = null;
        try {
            propertiesFile = new FileOutputStream(configPath);
            properties.store(propertiesFile, null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
