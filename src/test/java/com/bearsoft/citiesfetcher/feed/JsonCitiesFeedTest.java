package com.bearsoft.citiesfetcher.feed;

import com.bearsoft.citiesfetcher.model.PartialCityJsonException;
import com.bearsoft.citiesfetcher.JsonCitiesFeed;
import com.bearsoft.citiesfetcher.model.City;
import com.fasterxml.jackson.core.io.JsonEOFException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test suite for JSON object to city transformation.
 *
 * @author mg
 */
public final class JsonCitiesFeedTest {

    /**
     * Test city longitude.
     */
    private static final double TEST_LONGITUDE = 120.8d;
    /**
     * Test city latitude.
     */
    private static final double TEST_LATITUDE = 80.5d;
    /**
     * Test city id.
     */
    private static final int TEST_CITY_ID = 45;

    /**
     * This is simple test for {@code JsonCitiesFeed}.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test
    public void whenJsonCity() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader(""
                + "[{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"someObject\":"
                + " {\"a\": true, \"b\": [5, 6, {\"r\": -4, \"s\": false}, 8]}"
                + ", \"someArray\":"
                + " [{\"a\":7, \"b\": [], \"c\": true},"
                + "  {\"a\": true, \"b\": 78}]"
                + ", \"geo_position\":"
                + "{\"latitude\": 80.5, \"longitude\": 120.8,"
                + " \"so\": {\"latitude\": 456}, \"sa\": [{},{}]}"
                + ", \"location_id\": 377078"
                + "}]"
        ));
        Optional<City> read = feed.pull();
        assertEquals(TEST_CITY_ID, read.get().getId());
        assertEquals("Dusseldorf", read.get().getName());
        assertEquals("location", read.get().getType());
        assertEquals(TEST_LATITUDE, read.get().getLatitude(),
                Double.MIN_VALUE);
        assertEquals(TEST_LONGITUDE, read.get().getlongitude(),
                Double.MIN_VALUE);
    }

    /**
     * This is test for {@code JsonCitiesFeed} with empty Json.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test(expected = BadCitiesJsonException.class)
    public void whenJsonEmpty() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        JsonCitiesFeed.create(new StringReader(""));
    }

    /**
     * This is test for {@code JsonCitiesFeed} with unexpected array Json.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test(expected = BadCitiesJsonException.class)
    public void whenJsonUnexpectedArray() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader("[[]]"));
        feed.pull();
    }

    /**
     * This is test for {@code JsonCitiesFeed} with unexpected array json.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test(expected = JsonEOFException.class)
    public void whenJsonUnclosedArray() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader("["));
        feed.pull();
    }
}
