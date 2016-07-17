package com.bearsoft.citiesfetcher.transforms;

import com.bearsoft.citiesfetcher.CitiesFeed;
import com.bearsoft.citiesfetcher.model.City;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.io.StringReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test suite for JSON object to city transformation.
 *
 * @author mg
 */
public final class JsonToCitiesTest {

    /**
     * This is simple test for {@code JsonToCity}.
     *
     * @throws IOException if Json parser throws it.
     */
    @Test
    public void whenJsonFullCity() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\":"
                + "{\"latitude\": 80.5, \"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}"
        ));
        CitiesFeed transformer = new JsonToCities(parser);
        City read = transformer.pullNext();
        assertEquals(45, read.getId());
        assertEquals("Dusseldorf", read.getName());
        assertEquals("location", read.getType());
        assertEquals(80.5d, read.getLatitude(), Double.MIN_VALUE);
        assertEquals(120.8d, read.getlongitude(), Double.MIN_VALUE);
    }

    /**
     * This is test for how {@code JsonToCity} handles absent type field.
     *
     * @throws IOException if Json parser throws it.
     */
    @Test
    public void whenJsonCityWithoutType() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"someObject\": {\"a\": true, \"b\": [5, 6, {\"r\": -4, \"s\": false}, 8]}"
                + ", \"someArray\": [{\"a\":7, \"b\": [], \"c\": true}, {\"a\": true, \"b\": 78}]"
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\":"
                + "{\"latitude\": 80.5, \"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}"
        ));
        CitiesFeed transformer = new JsonToCities(parser);
        City read = transformer.pullNext();
        assertEquals(45, read.getId());
        assertEquals("Dusseldorf", read.getName());
        assertNull(read.getType());
        assertEquals(80.5d, read.getLatitude(), Double.MIN_VALUE);
        assertEquals(120.8d, read.getlongitude(), Double.MIN_VALUE);
    }

    /**
     * This is test for how {@code JsonToCity} handles absent name field.
     *
     * @throws IOException if Json parser throws it.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutName() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{\"_id\": 45"
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\":"
                + "{\"latitude\": 80.5, \"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}"
        ));
        CitiesFeed transformer = new JsonToCities(parser);
        transformer.pullNext();
    }

    /**
     * This is test for how {@code JsonToCity} handles absent id field.
     *
     * @throws IOException if Json parser throws it.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutId() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{\"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\":"
                + "{\"latitude\": 80.5, \"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}"
        ));
        CitiesFeed transformer = new JsonToCities(parser);
        transformer.pullNext();
    }

    /**
     * This is test for how {@code JsonToCity} handles absent latitude field.
     *
     * @throws IOException if Json parser throws it.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutLatitude() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\": {\"longitude\": 120.8}"
                + ", \"location_id\": 377078"
                + "}"
        ));
        CitiesFeed transformer = new JsonToCities(parser);
        transformer.pullNext();
    }

    /**
     * This is test for how {@code JsonToCity} handles absent longitude field.
     *
     * @throws IOException if Json parser throws it.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutlongitude() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"geo_position\": {\"latitude\": 80.5}"
                + ", \"location_id\": 377078"
                + "}"
        ));
        CitiesFeed transformer = new JsonToCities(parser);
        transformer.pullNext();
    }

    /**
     * This is test for how {@code JsonToCity} handles absent geo_position field
     * in first level object.
     *
     * @throws IOException if Json parser throws it.
     */
    @Test(expected = PartialCityJsonException.class)
    public void whenJsonCityWithoutGeoPosition() throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader(""
                + "{\"_id\": 45"
                + ", \"name\": \"Dusseldorf\""
                + ", \"type\": \"location\""
                + ", \"iata_airport_code\": null"
                + ", \"location_id\": 377078"
                + "}"
        ));
        CitiesFeed transformer = new JsonToCities(parser);
        transformer.pullNext();
    }
}
