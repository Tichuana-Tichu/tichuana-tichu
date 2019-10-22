package ch.tichuana.tichu.client.test;

import ch.tichuana.tichu.client.services.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    /**
     * Unit tests for client configuration Class.
     * @author Christian
     */
    private Configuration config = new Configuration("src/ch/tichuana/tichu/client/resources/config.properties");

    @Test
    public void testGetProperty(){
        assertEquals("de", config.getProperty("locale"));
    }

    @Test
    public void testSetProperty(){
        config.setProperty("testProperty","test");
        assertEquals("test", config.getProperty("testProperty"));
    }

    @Test
    public void testSave(){
        config.setProperty("testProperty","test");
        config.save();
        Configuration config2 = new Configuration("src/ch/tichuana/tichu/client/resources/config.properties");
        assertEquals("test", config2.getProperty("testProperty"));
    }

}