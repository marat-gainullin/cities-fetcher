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

    @FunctionalInterface
    private interface ObjectWalker {

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
    public JsonToCities(JsonParser aParser) {
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
    private static final String ARRAY_EXPECTED_MSG = "Expected startof an array";
    private static final String FINISH_OR_NEXT_OBJECT_EXPECTED_MSG
            = "Expected end of array or next object start";
    private static final String UNEXPECTED_ARRAY_MSG
            = "Unexpected start of an array";

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
                                if (parser.getCurrentToken() == JsonToken.START_OBJECT
                                        || parser.getCurrentToken() == JsonToken.START_ARRAY) {
                                    parser.skipChildren();
                                }
                                break;
                        }
                    });
                    break;
                default:
                    if (parser.getCurrentToken() == JsonToken.START_OBJECT
                            || parser.getCurrentToken() == JsonToken.START_ARRAY) {
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
     * @param aOnProperty
     * @throws IOException
     */
    private void walkObject(ObjectWalker aOnProperty) throws IOException {
        JsonToken token = parser.nextToken();
        while (token != JsonToken.END_OBJECT) {
            if (token != JsonToken.FIELD_NAME) {
                aOnProperty.apply();
            }
            token = parser.nextToken();
        }
    }
}
