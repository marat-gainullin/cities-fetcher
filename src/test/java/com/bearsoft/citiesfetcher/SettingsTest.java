package com.bearsoft.citiesfetcher;

import java.io.File;
import java.io.UnsupportedEncodingException;
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
     * @throws BadSettingsFormatException if the exception is thrown in
     * {@code Settings.parse()}.
     * @throws UnsupportedEncodingException if unsupported encoding used
     * somewhere in code.
     */
    @Test
    public final void whenOnlyCity() throws BadSettingsFormatException,
            UnsupportedEncodingException {
        Settings settings = Settings.parse("Berlin");
        assertTrue(settings.getCitySource().getPath().endsWith("Berlin"));
        assertEquals(new File("Berlin.csv"), settings.getDestination());
    }

    /**
     * Tests a case when file options is specified.
     *
     * @throws BadSettingsFormatException if the exception is thrown in
     * {@code Settings.parse()}.
     * @throws UnsupportedEncodingException if unsupported encoding used
     * somewhere in code.
     */
    @Test
    public final void whenCityWithFile() throws BadSettingsFormatException,
            UnsupportedEncodingException {
        Settings settings = Settings.parse("Berlin", "berlin-out.csv");
        assertTrue(settings.getCitySource().getPath().endsWith("Berlin"));
        assertEquals(new File("berlin-out.csv"), settings.getDestination());
    }

    /**
     * Tests a case when extra arguments present.
     *
     * @throws BadSettingsFormatException if the exception is thrown in
     * {@code Settings.parse()}.
     * @throws UnsupportedEncodingException if unsupported encoding used
     * somewhere in code.
     */
    @Test(expected = BadSettingsFormatException.class)
    public final void whenExtraArguments() throws BadSettingsFormatException,
            UnsupportedEncodingException {
        Settings.parse("Frankfurt", "am", "mein");
    }
}
