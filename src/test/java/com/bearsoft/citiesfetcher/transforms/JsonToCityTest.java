/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher.transforms;

import com.bearsoft.citiesfetcher.model.City;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.function.Supplier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Test suite for JSON object to city transformation.
 *
 * @author mg
 */
public class JsonToCityTest {

    @Test
    public void whenJsonFullCity() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{_id: 45"
                + ", name: 'Dusseldorf'"
                + ", type: 'location'"
                + ", iata_airport_code: null"
                + ", geo_position: {latitude: 80.5, longtitude: 120.8}"
                + ", location_id: 377078"
                + "}"
        ));
        Supplier<City> transformer = new JsonToSity(parser);
        City read = transformer.get();
        assertEquals(45, read.getId());
        assertEquals("Dusseldorf", read.getName());
        assertEquals("location", read.getType());
        assertEquals(80.5d, read.getLatitude(), Double.MIN_VALUE);
        assertEquals(120.8d, read.getLongtitude(), Double.MIN_VALUE);
    }

    @Test
    public void whenJsonCityWithoutType() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{_id: 45"
                + ", name: 'Dusseldorf'"
                + ", iata_airport_code: null"
                + ", geo_position: {latitude: 80.5, longtitude: 120.8}"
                + ", location_id: 377078"
                + "}"
        ));
        Supplier<City> transformer = new JsonToSity(parser);
        City read = transformer.get();
        assertEquals(45, read.getId());
        assertEquals("Dusseldorf", read.getName());
        assertNull(read.getType());
        assertEquals(80.5d, read.getLatitude(), Double.MIN_VALUE);
        assertEquals(120.8d, read.getLongtitude(), Double.MIN_VALUE);
    }

    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutName() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{_id: 45"
                + ", type: 'location'"
                + ", iata_airport_code: null"
                + ", geo_position: {latitude: 80.5, longtitude: 120.8}"
                + ", location_id: 377078"
                + "}"
        ));
        Supplier<City> transformer = new JsonToSity(parser);
        transformer.get();
    }

    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutId() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{name: 'Dusseldorf'"
                + ", type: 'location'"
                + ", iata_airport_code: null"
                + ", geo_position: {latitude: 80.5, longtitude: 120.8}"
                + ", location_id: 377078"
                + "}"
        ));
        Supplier<City> transformer = new JsonToSity(parser);
        transformer.get();
    }

    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutLatitude() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{id: 45"
                + ", name: 'Dusseldorf'"
                + ", type: 'location'"
                + ", iata_airport_code: null"
                + ", geo_position: {longtitude: 120.8}"
                + ", location_id: 377078"
                + "}"
        ));
        Supplier<City> transformer = new JsonToSity(parser);
        transformer.get();
    }

    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutLongtitude() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{id: 45"
                + ", name: 'Dusseldorf'"
                + ", type: 'location'"
                + ", iata_airport_code: null"
                + ", geo_position: {latitude: 80.5}"
                + ", location_id: 377078"
                + "}"
        ));
        Supplier<City> transformer = new JsonToSity(parser);
        transformer.get();
    }

    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutGeoPosition() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{id: 45"
                + ", name: 'Dusseldorf'"
                + ", type: 'location'"
                + ", iata_airport_code: null"
                + ", location_id: 377078"
                + "}"
        ));
        Supplier<City> transformer = new JsonToSity(parser);
        transformer.get();
    }
}
