/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.model.City;
import com.bearsoft.citiesfetcher.feed.BadCitiesJsonException;
import com.bearsoft.citiesfetcher.feed.CitiesFeed;
import com.bearsoft.citiesfetcher.model.PartialCityJsonException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Json to city transformer. Transforms a part of Json tokens stream from
 * {@code JsonParser} to {@code City} instance. It can be called multiple times
 * for pulling data of consecutive cities from Json tokens stream. This class is
 * here because of it makes us free to use only 'jackson-core' library.
 *
 * @author mg
 * @see City
 * @see JsonParser
 */
public final class JsonCitiesFeed implements CitiesFeed {

    /**
     * A {@code Runnable} like interface with exceptions.
     */
    @FunctionalInterface
    private interface Callback {

        /**
         * Walker callback method with exception.
         *
         * @throws IOException if a IO problem occurs.
         */
        void apply() throws IOException;
    }

    /**
     * Json parser that is used as Json token source.
     */
    private final JsonParser parser;

    /**
     * {code JsonCitiesFeed} transformer constructor.
     *
     * @param aParser {@code JsonParser} to used as Json tokens source.
     */
    private JsonCitiesFeed(final JsonParser aParser) {
        parser = aParser;
    }

    /**
     * Pulls object tokens and reads {@code City} instance from underlying Json
     * parser.
     *
     * @return {@code City} instance, read from Json tokens stream.
     * @throws IOException if city information is not complete or if low level
     * {@code IOException} is thrown.
     * @throws PartialCityJsonException If some part of mandatory data is
     * absent.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    @Override
    public Optional<City> pull() throws IOException,
            PartialCityJsonException, BadCitiesJsonException {
        JsonToken start = parser.nextToken();
        switch (start) {
            case END_ARRAY:
                return Optional.empty();
            case START_OBJECT:
                return readObject();
            default:
                throw new BadCitiesJsonException(
                        FINISH_OR_NEXT_OBJECT_EXPECTED_MSG);
        }
    }
    /**
     * Message for exception when first token is not an array start.
     */
    private static final String ARRAY_EXPECTED_MSG
            = "Expected start of an array";
    /**
     * Message for exception when neither next object start, nor array end, but
     * some another token occurred while parsing objects at the middle of cities
     * array.
     */
    private static final String FINISH_OR_NEXT_OBJECT_EXPECTED_MSG
            = "Expected end of array or next object start";

    /**
     * Reads a object from Json token stream.
     *
     * @return {code Optional<City>} instance.
     * @throws IOException if a problem with IO occurred.
     * @throws PartialCityJsonException if some mandatory fields of {@code City}
     * are missing.
     */
    private Optional<City> readObject() throws IOException,
            PartialCityJsonException {
        City.Builder builder = new City.Builder();
        walkObject(() -> {
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case "_id":
                    builder.id(parser.getLongValue());
                    break;
                case "name":
                    builder.name(parser.getText());
                    break;
                case "type":
                    builder.type(parser.getText());
                    break;
                case "geo_position":
                    walkObject(() -> {
                        String geoFieldName = parser.getCurrentName();
                        switch (geoFieldName) {
                            case "latitude":
                                builder.latitude(parser.getDoubleValue());
                                break;
                            case "longitude":
                                builder.longitude(parser.getDoubleValue());
                                break;
                            default:
                                skipObjectOrArray();
                                break;
                        }
                    });
                    break;
                default:
                    skipObjectOrArray();
                    break;
            }
        });
        return Optional.of(builder.toCity());
    }

    /**
     * Skips current object or array. Stops on {@code JsonToken.END_OBJECT} or
     * {@code JsonToken.END_ARRAY} token.
     *
     * @throws IOException if a problem with IO occurred.
     */
    private void skipObjectOrArray() throws IOException {
        if (parser.getCurrentToken() == JsonToken.START_OBJECT
                || parser.getCurrentToken() == JsonToken.START_ARRAY) {
            parser.skipChildren();
        }
    }

    /**
     * Encapsulates logic of object's fields traversing. It is minimalistic and
     * doesn't support arrays, because implementation of full Json logic here
     * would be over engineering.
     *
     * @param aOnProperty {@code ObjectWalker} instance to be notified about
     * properties.
     * @throws IOException if a problem with IO occurred.
     */
    private void walkObject(final Callback aOnProperty) throws IOException {
        JsonToken token = parser.nextToken();
        while (token != JsonToken.END_OBJECT) {
            if (token != JsonToken.FIELD_NAME) {
                aOnProperty.apply();
            }
            token = parser.nextToken();
        }
    }

    /**
     * Creates {@code JsonCitiesFeed} instance and advances {@code aStream} to
     * first start object token.
     *
     * @param aStream A stream to be used as a source of Json tokens.
     * @param aCharset A charset of Json.
     * @return {@code JsonCitiesFeed} instance initialized with an advanced
     * stream.
     * @throws IOException if a problem with IO occurs.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    public static JsonCitiesFeed create(
            final InputStream aStream, final Charset aCharset)
            throws IOException, BadCitiesJsonException {
        Reader reader = new InputStreamReader(aStream, aCharset);
        return create(reader);
    }

    /**
     * Creates {@code JsonCitiesFeed} instance and advances {@code aStream} to
     * first start object token.
     *
     * @param aReader A reader to be used as a source of Json tokens.
     * @return {@code JsonCitiesFeed} instance initialized with an advanced
     * stream.
     * @throws IOException if a problem with IO occurs.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    public static JsonCitiesFeed create(final Reader aReader)
            throws IOException, BadCitiesJsonException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser parser = jsonFactory.createParser(aReader);
        JsonToken start = parser.nextToken();
        if (start == JsonToken.START_ARRAY) {
            return new JsonCitiesFeed(parser);
        } else {
            throw new BadCitiesJsonException(ARRAY_EXPECTED_MSG);
        }
    }
}
