/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author mg
 */
public class CityCsvTransformerTest {

    @Test
    public void whenUnescapedTest() throws IOException {
        City item = new City(1, "Wonderful city", "location", -20.2, 54);
        Supplier<StringBuilder> transformer = new CityCsvTransformer(item);
        StringBuilder line = transformer.get();
        assertEquals("1,Wonderful city,location,-20.2,54", line.toString());
    }
    
    @Test
    public void whenEscapedTest() throws IOException {
        City item = new City(1, "Wonderful, \"city\"", "loc\r\nation", -20.2, 54);
        Supplier<StringBuilder> transformer = new CityCsvTransformer(item);
        StringBuilder line = transformer.get();
        assertEquals("1,\"Wonderful, \"\"city\"\"\",\"location\",-20.2,54", line.toString());
    }
}
