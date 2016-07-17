/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import java.io.IOException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Fetcher test suite.
 *
 * @author mg
 */
public class FetcherTest {

    @Test
    public void whenSimpleCity() throws IOException, BadSettingsFormatException {
        assertTrue(Fetcher.run(new String[]{
            "Berlin"
        }) > 0);
    }

    @Test
    public void whenComplexCity() throws IOException, BadSettingsFormatException {
        assertTrue(Fetcher.run(new String[]{
            "Frankfurt am mein"
        }) > 0);
    }
}
