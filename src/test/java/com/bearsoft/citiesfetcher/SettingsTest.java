/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import java.nio.file.Paths;
import org.junit.Test;

/**
 *
 * @author mg
 */
public class SettingsTest {
    
    @Test
    public void whenOnlyCity(){
        Setings settings = Settings.parse("Berlin");
        assertEquals("Berlin", settings.getCity());
        assertEquals(Paths.get("~/Berlin.csv"), settings.getPath());
    }
    
    @Test
    public void whenOnlyCityQuoted(){
        Setings settings = Settings.parse("\"Frankfurt am mein\"");
        assertEquals("Frankfurt am mein", settings.getCity());
        assertEquals(Paths.get("~/Frankfurt am mein.csv"), settings.getPath());
    }
    
    @Test
    public void whenCityWithFile(){
        Setings settings = Settings.parse("Berlin -f berlin-out.csv");
        assertEquals("Berlin", settings.getCity());
        assertEquals(Paths.get("berlin-out.csv"), settings.getPath());
    }
    
    @Test(expected = BadSettingsFormatException.class)
    public void whenCityWithOrphanFileSwitch(){
        Setings settings = Settings.parse("Berlin -f ");
    }
}
