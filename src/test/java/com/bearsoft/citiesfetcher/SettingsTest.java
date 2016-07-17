package com.bearsoft.citiesfetcher;

import java.nio.file.Paths;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test suite for {@code Settings}.
 *
 * @author mg
 * @see Settings
 */
public class SettingsTest {

    /**
     * Tests a case when only one cargument present.
     *
     * @throws BadSettingsFormatException
     * if the exception is thrown in {@code Settings.parse()}.
     */
    @Test
    public final void whenOnlyCity() throws BadSettingsFormatException {
        Settings settings = Settings.parse("Berlin");
        assertTrue(settings.getCitySource().getPath().endsWith("Berlin"));
        assertEquals(Paths.get("Berlin.csv"), settings.getDestination());
    }

    /**
     * Tests a case when file options is specified.
     *
     * @throws BadSettingsFormatException
     * if the exception is thrown in {@code Settings.parse()}.
     */
    @Test
    public final void whenCityWithFile() throws BadSettingsFormatException {
        Settings settings = Settings.parse("Berlin", "-f", "berlin-out.csv");
        assertTrue(settings.getCitySource().getPath().endsWith("Berlin"));
        assertEquals(Paths.get("berlin-out.csv"), settings.getDestination());
    }

    /**
     * Tests a case when file option goes without its value.
     *
     * @throws BadSettingsFormatException
     * if the exception is thrown in {@code Settings.parse()}.
     */
    @Test(expected = BadSettingsFormatException.class)
    public final void whenCityWithOrphanFileSwitch()
            throws BadSettingsFormatException {
        Settings.parse("Berlin", "-f");
    }

    /**
     * Tests a case when extra arguments present.
     *
     * @throws BadSettingsFormatException
     * if the exception is thrown in {@code Settings.parse()}.
     */
    @Test(expected = BadSettingsFormatException.class)
    public final void whenExtraArguments() throws BadSettingsFormatException {
        Settings.parse("Frankfurt", "am", "mein");
    }
}
