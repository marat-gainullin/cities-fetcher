/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher.transforms;

import com.bearsoft.citiesfetcher.CitiesFeed;
import com.bearsoft.citiesfetcher.model.City;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Optional;

/**
 * Json to city transformer. Transforms a part of json tokens stream from
 * {@code JsonParser} to {@code City} instnace. It can be called multiple times
 * for pulling data of various cities from json.
 *
 * @author mg
 * @see City
 * @see JsonParser
 */
public final class JsonToCities implements CitiesFeed {

    /**
     * {@code Runnable} anlong interface with exceptions.
     */
    @FunctionalInterface
    private interface ObjectWalker {

        /**
         * Walker callback method with exception.
         *
         * @throws IOException if a IO problem occures.
         */
        void apply() throws IOException;
    }

    /**
     * Json parser that is used as json token source.
     */
    private final JsonParser parser;

    /**
     * Indicates whether we have started to traverse ajson array or not.
     */
    private boolean started;

    /**
     * {code JsonToCity} transformer constructor.
     *
     * @param aParser {@code JsonParser} to used as json tokens source.
     */
    public JsonToCities(final JsonParser aParser) {
        parser = aParser;
    }

    /**
     * Pulls object tokens and reads {@code City} instance from underlying json
     * parser.
     *
     * @return {@code City} instance, read fron json tokens stream.
     * @throws IOException if city information is not complete or if low level
     * {@code IOException} is thrown.
     */
    @Override
    public Optional<City> pull() throws IOException {
        JsonToken start = parser.nextToken();
        if (start != null) {
            if (start == JsonToken.START_ARRAY) {
                if (started) {
                    throw new IOException(UNEXPECTED_ARRAY_MSG);
                } else {
                    started = true;
                    return pull();
                }
            } else if (!started) {
                throw new IOException(ARRAY_EXPECTED_MSG);
            }
            switch (start) {
                case END_ARRAY:
                    return Optional.empty();
                case START_OBJECT:
                    return readObject();
                default:
                    throw new IOException(FINISH_OR_NEXT_OBJECT_EXPECTED_MSG);
            }
        } else {
            return Optional.empty();
        }
    }
    /**
     * Message for exception when frist token is not array start.
     */
    private static final String ARRAY_EXPECTED_MSG
            = "Expected start of an array";
    /**
     * Message for exception when neither next object start, nor array end, but
     * some another token occured while parsing objects at the midle of cities
     * array.
     */
    private static final String FINISH_OR_NEXT_OBJECT_EXPECTED_MSG
            = "Expected end of array or next object start";
    /**
     * Message for exception when array start token occured again after start of
     * cities array.
     */
    private static final String UNEXPECTED_ARRAY_MSG
            = "Unexpected start of an array";

    /**
     * Reads a object from json token stream.
     *
     * @return {code Optional<City>} instance.
     * @throws IOException if a problem with IO occured.
     */
    private Optional<City> readObject() throws IOException {
        CityBuilder builder = new CityBuilder();
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
                                if (parser.getCurrentToken()
                                        == JsonToken.START_OBJECT
                                        || parser.getCurrentToken()
                                        == JsonToken.START_ARRAY) {
                                    parser.skipChildren();
                                }
                                break;
                        }
                    });
                    break;
                default:
                    if (parser.getCurrentToken()
                            == JsonToken.START_OBJECT
                            || parser.getCurrentToken()
                            == JsonToken.START_ARRAY) {
                        parser.skipChildren();
                    }
                    break;
            }
        });
        return Optional.of(builder.toCity());
    }

    /**
     * Incapsulates logic of object's fields traversing. It is minimalistic and
     * doesn't support arrays, because implementation of full json logic here
     * would be over engineering.
     *
     * @param aOnProperty {@code ObjectWalker} instance to be notified about
     * properties.
     * @throws IOException if a problem with IO occured.
     */
    private void walkObject(final ObjectWalker aOnProperty) throws IOException {
        JsonToken token = parser.nextToken();
        while (token != JsonToken.END_OBJECT) {
            if (token != JsonToken.FIELD_NAME) {
                aOnProperty.apply();
            }
            token = parser.nextToken();
        }
    }
}
