/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Settings containser. It is parsed from string array, e.g. from command line
 * arguments.
 *
 * @author mg
 */
public class Settings {

    private static final String ENDPOINT_TEMPLATE
            = "http://api.goeuro.com/api/v2/position/suggest/en/%s";

    /**
     * Cities source url.
     */
    private final URL citiesSource;
    /**
     * Destination file.
     */
    private final File destination;

    /**
     * Constructor of settings. protected because it is intended for creation by
     * factory method.
     *
     * @param aCitySource Url of cities source end point.
     * @param aDestination A path to output file.
     */
    protected Settings(final URL aCitySource, final File aDestination) {
        super();
        citiesSource = aCitySource;
        destination = aDestination;
    }

    /**
     * City name getter.
     *
     * @return City name.
     */
    public final URL getCitySource() {
        return citiesSource;
    }

    /**
     * Destination file getter.
     *
     * @return A {@code File} of the output file.
     * @see File
     */
    public final File getDestination() {
        return destination;
    }

    /**
     * Parses {@code args} array and instantiates {@code Settings} initialized
     * with values from {@code args}.
     *
     * @param args String arguments array to be parsed.
     * @return {@code Settings} instance initialized with parameters from
     * {@code args}.
     * @throws BadSettingsFormatException if some unexpected argument occured.
     */
    public static Settings parse(final String... args)
            throws BadSettingsFormatException {
        try {
            switch (args.length) {
                case ONLY_CITY_ARGS_LENGTH:
                    return new Settings(new URL(String.format(ENDPOINT_TEMPLATE, args[0])), Paths.get(args[0] + ".csv").toFile());
                case WITH_FILE_ARGS_LENGTH:
                    return new Settings(new URL(String.format(ENDPOINT_TEMPLATE, args[0])), Paths.get(args[1]).toFile());
                default:
                    throw new BadSettingsFormatException("One argument \"CITY_NAME\" or two arguments \"CITY_NAME\" file-name.csv are expected.");
            }
        } catch (MalformedURLException ex) {
            throw new IllegalStateException(ex);
        }
    }
    /**
     * Arguments length expected if only city argument present.
     */
    private static final int ONLY_CITY_ARGS_LENGTH = 1;
    /**
     * Arguments length expected if file-name option is used.
     */
    private static final int WITH_FILE_ARGS_LENGTH = 2;
}
