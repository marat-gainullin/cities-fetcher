package com.bearsoft.citiesfetcher.transforms;

import com.bearsoft.citiesfetcher.model.City;
import java.util.function.Supplier;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test suite for {@code CityToCsv} transformer.
 *
 * @author mg
 */
public class CityToCsvTest {

    /**
     * Tests a case when no symbols to escape found in values.
     */
    @Test
    public final void whenUnescapedTest() {
        City item = new City(1, " Wonderful city", "location", -20.2d, 54d);
        Supplier<StringBuilder> transformer = new CityToCsv(item);
        StringBuilder line = transformer.get();
        assertEquals(
                "1, Wonderful city,location,-20.2,54.0\r\n", line.toString());
    }

    /**
     * Tests a case when symbols to escape found in values.
     */
    @Test
    public final void whenEscapedTest() {
        City item = new City(
                1,
                "Wonderful, \"city\"", "loc\r\nation", -20.2d, 54d);
        Supplier<StringBuilder> transformer = new CityToCsv(item);
        StringBuilder line = transformer.get();
        assertEquals(
                "1,\"Wonderful, \"\"city\"\"\",\"loc\r\nation\",-20.2,54.0\r\n",
                line.toString());
    }
    
    /**
     * Tests a case when nulls are present in values.
     */
    @Test
    public final void whenNullsTest() {
        City item = new City(1, " Wonderful city", null, -20.2d, 54d);
        Supplier<StringBuilder> transformer = new CityToCsv(item);
        StringBuilder line = transformer.get();
        assertEquals(
                "1, Wonderful city,,-20.2,54.0\r\n", line.toString());
    }

}