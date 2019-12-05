package ch.tichuana.tichu.client.test;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    private Translator translator;

    @BeforeAll
    public static void addServiceLocator(){
        ServiceLocator.getServiceLocator().setConfiguration(new Configuration("src/ch/tichuana/tichu/client/resources/config.properties"));

    }

    @Test
    public void testGetString(){
        translator = new Translator("en");
        String string = translator.getString("hello");
        assertEquals("hello", string);

        translator = new Translator("de");
        string = translator.getString("hello");
        assertEquals("hallo", string);


    }
}