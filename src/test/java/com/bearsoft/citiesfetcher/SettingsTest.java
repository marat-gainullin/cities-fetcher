/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import java.nio.file.Paths;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author mg
 */
public class SettingsTest {

    @Test
    public void whenOnlyCity() throws BadSettingsFormatException {
        Settings settings = Settings.parse("Berlin");
        assertEquals("Berlin", settings.getCityName());
        assertEquals(Paths.get("Berlin.csv"), settings.getDestination());
    }

    @Test
    public void whenCityWithFile() throws BadSettingsFormatException {
        Settings settings = Settings.parse("Berlin", "-f", "berlin-out.csv");
        assertEquals("Berlin", settings.getCityName());
        assertEquals(Paths.get("berlin-out.csv"), settings.getDestination());
    }

    @Test(expected = BadSettingsFormatException.class)
    public void whenCityWithOrphanFileSwitch() throws BadSettingsFormatException {
        Settings.parse("Berlin", "-f");
    }

    @Test(expected = BadSettingsFormatException.class)
    public void whenExtraArguments() throws BadSettingsFormatException {
        Settings.parse("Frankfurt", "am", "mein");
    }
}
