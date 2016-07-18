/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher.model;

import com.bearsoft.citiesfetcher.JsonCitiesFeed;
import com.bearsoft.citiesfetcher.feed.BadCitiesJsonException;
import com.bearsoft.citiesfetcher.feed.CitiesFeed;
import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Test suite for {@code City.Builder}. Test its ability to control mandatory
 * fields presence.
 *
 * @author mg
 */
public final class CityBuilderTest {

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
     * This is test for how {@code JsonCitiesFeed} handles absent type field.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test
    public void whenJsonCityWithoutType() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader(""
                + "[{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"someObject\": {\"a\": true,"
                + " \"b\": [5, 6, {\"r\": -4, \"s\": false}, 8]}"
                + ", \"someArray\": [{\"a\":7,"
                + " \"b\": [], \"c\": true}, {\"a\": true, \"b\": 78}]"
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\":"
                + "{\"latitude\": 80.5, \"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}]"
        ));
        Optional<City> read = feed.pull();
        assertEquals(TEST_CITY_ID, read.get().getId());
        assertEquals("Dusseldorf", read.get().getName());
        assertNull(read.get().getType());
        assertEquals(TEST_LATITUDE, read.get().getLatitude(),
                Double.MIN_VALUE);
        assertEquals(TEST_LONGITUDE, read.get().getlongitude(),
                Double.MIN_VALUE);
    }

    /**
     * This is test for how {@code JsonCitiesFeed} handles absent name field.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutName() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader(""
                + "[{\"_id\": 45"
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\":"
                + "{\"latitude\": 80.5, \"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}]"
        ));
        feed.pull();
    }

    /**
     * This is test for how {@code JsonCitiesFeed} handles absent id field.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutId() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader(""
                + "[{\"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\":"
                + "{\"latitude\": 80.5, \"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}]"
        ));
        feed.pull();
    }

    /**
     * This is test for how {@code JsonCitiesFeed} handles absent latitude
     * field.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutLatitude() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader(""
                + "[{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\": {\"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}]"
        ));
        feed.pull();
    }

    /**
     * This is test for how {@code JsonCitiesFeed} handles absent longitude
     * field.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutlongitude() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader(""
                + "[{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\": {\"latitude\": 80.5}"
                + ", \"location_id\": 377078"
                + "}]"
        ));
        feed.pull();
    }

    /**
     * This is test for how {@code JsonCitiesFeed} handles absent geo_position
     * field in first level object.
     *
     * @throws IOException if Json parser throws it.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutGeoPosition() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException {
        CitiesFeed feed = JsonCitiesFeed.create(new StringReader(""
                + "[{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"location_id\": 377078"
                + "}]"
        ));
        feed.pull();
    }
}
