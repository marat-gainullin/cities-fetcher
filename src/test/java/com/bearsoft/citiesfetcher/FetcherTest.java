package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.transforms.PartialCityJsonException;
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
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     */
    @Test
    public void whenSimpleCity() throws IOException,
            BadSettingsFormatException,
            PartialCityJsonException {
        assertTrue(Fetcher.run(new String[]{
            "Berlin"
        }) > 0);
    }

    /**
     * Tests a case with complex city name.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadSettingsFormatException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     */
    @Test
    public void whenComplexCity() throws IOException,
            BadSettingsFormatException,
            PartialCityJsonException {
        assertTrue(Fetcher.run(new String[]{
            "Frankfurt am Main"
        }) > 0);
    }

    /**
     * Tests a case with unexistent city.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadSettingsFormatException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     */
    @Test
    public void whenUnexistentCity() throws IOException,
            BadSettingsFormatException,
            PartialCityJsonException {
        assertEquals(0, Fetcher.run(new String[]{
            "Some unexistent city :)"
        }));
    }

}
