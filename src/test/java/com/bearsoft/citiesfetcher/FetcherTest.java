package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.feed.BadCitiesJsonException;
import com.bearsoft.citiesfetcher.model.PartialCityJsonException;
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
     * @throws BadArgumentsException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test
    public void whenSimpleCity() throws IOException,
            BadArgumentsException,
            PartialCityJsonException,
            BadCitiesJsonException {
        assertTrue(Fetcher.run(new String[]{
            "Berlin"
        }) > 0);
    }

    /**
     * Tests a case with complex city name.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadArgumentsException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test
    public void whenComplexCity() throws IOException,
            BadArgumentsException,
            PartialCityJsonException,
            BadCitiesJsonException {
        assertTrue(Fetcher.run(new String[]{
            "Frankfurt am Main"
        }) > 0);
    }

    /**
     * Tests a case with unexistent city.
     *
     * @throws IOException if it is thrown in {@code Fetcher} code.
     * @throws BadArgumentsException if settings are bad.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test
    public void whenUnexistentCity() throws IOException,
            BadArgumentsException,
            PartialCityJsonException,
            BadCitiesJsonException {
        assertEquals(0, Fetcher.run(new String[]{
            "Some unexistent city :)"
        }));
    }

}
