/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Settings container. It is parsed from string array, e.g. from command line
 * arguments.
 *
 * @author mg
 */
public class Settings {

    /**
     * GoEuro JSON API endpoint.
     */
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
     * @see URL
     * @see File
     */
    protected Settings(final URL aCitySource, final File aDestination) {
        super();
        citiesSource = aCitySource;
        destination = aDestination;
    }

    /**
     * City source getter.
     *
     * @return City source URL.
     * @see URL
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
     * Eliminates bad symbols from aname to make a valid filename.
     *
     * @param aName A name to be transformed.
     * @return Transformed name that can be used as a file name.
     */
    private static String fileName(final String aName) {
        String name = aName.replaceAll(
                "[\\s\\^#%&{}<>\\*\\? $!'\":@+`|=]", "_");
        name = name.replace('\\', '/').replace('/', File.separatorChar);

        if (name.toLowerCase().endsWith(CSV_FILE_NAME_END)) {
            return name;
        } else {
            return name + CSV_FILE_NAME_END;
        }
    }

    /**
     * CSV file name end (.csv).
     */
    private static final String CSV_FILE_NAME_END = ".csv";

    /**
     * Parses {@code args} array and instantiates {@code Settings} initialized
     * with values from {@code args}.
     *
     * @param args String arguments array to be parsed.
     * @return {@code Settings} instance initialized with parameters from
     * {@code args}.
     * @throws BadArgumentsException if some unexpected argument occurred.
     * @throws UnsupportedEncodingException if code will use unsupported
     * encoding while {@code URLEncoder.encode}
     */
    public static Settings parse(final String... args)
            throws BadArgumentsException, UnsupportedEncodingException {
        try {
            if (args.length > 0) {
                URL url = new URL(String.format(ENDPOINT_TEMPLATE,
                        URLEncoder.encode(args[0],
                                StandardCharsets.UTF_8.name())
                        .replace("+", "%20")));
                switch (args.length) {
                    case ONLY_CITY_ARGS_LENGTH:
                        return new Settings(url,
                                new File(fileName(args[0])));
                    case WITH_FILE_ARGS_LENGTH:
                        return new Settings(url,
                                new File(fileName(args[1])));
                    default:
                        throw new BadArgumentsException(
                                ARGUMENTS_EXPECTED_MSG);
                }
            } else {
                throw new BadArgumentsException(ARGUMENTS_EXPECTED_MSG);
            }
        } catch (MalformedURLException ex) {
            throw new IllegalStateException(ex);
        }
    }
    /**
     * Message displayed if no arguments paased or too much arguments passed.
     */
    private static final String ARGUMENTS_EXPECTED_MSG = ""
            + "One argument \"CITY_NAME\" or two arguments \"CITY_NAME\" "
            + "file-name.csv are expected.";
    /**
     * Arguments length expected if only city argument present.
     */
    private static final int ONLY_CITY_ARGS_LENGTH = 1;
    /**
     * Arguments length expected if file-name option is used.
     */
    private static final int WITH_FILE_ARGS_LENGTH = 2;
}
