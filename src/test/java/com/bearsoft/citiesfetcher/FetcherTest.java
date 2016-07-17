package com.bearsoft.citiesfetcher;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Fetcher test suite.
 *
 * @author mg
 */
public final class FetcherTest {

    /**
     * Tests a case with simple city name.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadSettingsFormatException if settings are bad.
     */
    @Test
    public void whenSimpleCity() throws IOException,
            BadSettingsFormatException {
        assertTrue(Fetcher.run(new String[]{
            "Berlin"
        }) > 0);
    }

    /**
     * Tests a case with complex city name.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadSettingsFormatException if settings are bad.
     */
    @Test
    public void whenComplexCity() throws IOException,
            BadSettingsFormatException {
        assertTrue(Fetcher.run(new String[]{
            "Frankfurt am Main"
        }) > 0);
    }

    /**
     * Tests a case with unexistent city.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadSettingsFormatException if settings are bad.
     */
    @Test
    public void whenUnexistentCity() throws IOException,
            BadSettingsFormatException {
        assertEquals(0, Fetcher.run(new String[]{
            "Some unexistent city :)"
        }));
    }

}
