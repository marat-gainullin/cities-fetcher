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
     * {code JsonToCity} transformer constructor.
     *
     * @param aParser {@code JsonParser} to used as json tokens source.
     */
    public JsonToCities(JsonParser aParser) {
        parser = aParser;
    }

    /**
     * Json parser getter. It is used to determine what parser is used by this
     * transformer.
     *
     * @return {@code JsonParser} instance.
     * @see JsonParser
     */
    public JsonParser getParser() {
        return parser;
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
    public City pullNext() throws IOException {
        JsonToken objectStart = parser.nextToken();
        assert objectStart == JsonToken.START_OBJECT :
                "Json object expected.";
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
                            case "longtitude":
                                builder.longtitude(parser.getDoubleValue());
                                break;
                        }
                    });
                    break;
            }
        });
        return builder.toCity();
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
