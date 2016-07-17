/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Settings containser. It is parsed from string array, e.g. from command line
 * arguments.
 *
 * @author mg
 */
public class Settings {

    /**
     * City name setting.
     */
    private final String cityName;
    /**
     * Destination path setting.
     */
    private final Path destination;

    /**
     * Constructor of settings. protected because it is intended for creation by
     * factory method.
     *
     * @param aCityName Name of the city in this settings.
     * @param aDestination A path to output file.
     */
    protected Settings(final String aCityName, final Path aDestination) {
        super();
        cityName = aCityName;
        destination = aDestination;
    }

    /**
     * City name getter.
     *
     * @return City name.
     */
    public final String getCityName() {
        return cityName;
    }

    /**
     * Destination path getter.
     *
     * @return A {@code Path} of the output file.
     * @see Path
     */
    public final Path getDestination() {
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
        switch (args.length) {
            case ONLY_CITY_ARGS_LENGTH:
                return new Settings(args[0], Paths.get(args[0] + ".csv"));
            case WITH_FILE_ARGS_LENGTH:
                if ("-f".equals(args[1])) {
                    return new Settings(args[0], Paths.get(args[2]));
                } else {
                    throw new BadSettingsFormatException();
                }
            default:
                throw new BadSettingsFormatException();
        }
    }
    /**
     * Arguments length expected if only city argument present.
     */
    private static final int ONLY_CITY_ARGS_LENGTH = 1;
    /**
     * Arguments length expected if -f option is used.
     */
    private static final int WITH_FILE_ARGS_LENGTH = 3;
}
