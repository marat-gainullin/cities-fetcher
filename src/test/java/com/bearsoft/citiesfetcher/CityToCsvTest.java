package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.model.City;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test suite for {@code CityToCsv} transformer.
 *
 * @author mg
 */
public final class CityToCsvTest {

    /**
     * Tests a case when no symbols to escape found in values.
     */
    @Test
    public void whenUnescapedTest() {
        City item = new City(TEST_CITY_ID, " Wonderful city", "location",
                TEST_LATITUDE, TEST_LONGITUDE);
        StringBuilder line = Csv.to(item);
        assertEquals(
                "1, Wonderful city,location,-20.2,54.0\r\n", line.toString());
    }
    /**
     * Test city id.
     */
    private static final int TEST_CITY_ID = 1;
    /**
     * Test latitude value.
     */
    private static final double TEST_LATITUDE = -20.2d;
    /**
     * Test longitude.
     */
    private static final double TEST_LONGITUDE = 54d;

    /**
     * Tests a case when symbols to escape found in values.
     */
    @Test
    public void whenEscapedTest() {
        City item = new City(
                TEST_CITY_ID,
                "Wonderful, \"city\"", "loc\r\nation",
                TEST_LATITUDE, TEST_LONGITUDE);
        StringBuilder line = Csv.to(item);
        assertEquals(
                "1,\"Wonderful, \"\"city\"\"\",\"loc\r\nation\",-20.2,54.0\r\n",
                line.toString());
    }

    /**
     * Tests a case when nulls are present in values.
     */
    @Test
    public void whenNullsTest() {
        City item = new City(TEST_CITY_ID, " Wonderful city", null,
                TEST_LATITUDE, TEST_LONGITUDE);
        StringBuilder line = Csv.to(item);
        assertEquals(
                "1, Wonderful city,,-20.2,54.0\r\n", line.toString());
    }

}
