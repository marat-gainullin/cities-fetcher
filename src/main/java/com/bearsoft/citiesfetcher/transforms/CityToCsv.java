/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher.transforms;

import com.bearsoft.citiesfetcher.model.City;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Transforms a {@code City} instance to CSV line.
 *
 * @author mg
 */
public class CityToCsv implements Supplier<StringBuilder> {

    /**
     * Pattern for discover if a value contains symbols to be escaped.
     */
    private static final Pattern ESCAPED_TEMPLATE
            = Pattern.compile(".*[,\r\n\"].*", Pattern.DOTALL);
    /**
     * Pattern for escaping quotes with another quotes.
     */
    private static final Pattern QUOTES_TEMPLATE = Pattern.compile("\"");

    /**
     * {@code City} instance to be transformed to CSV line.
     *
     * @see City
     */
    private final City data;

    /**
     * City to CSV tranformer constructor.
     *
     * @param aData A {@code City} instance to be transformed.
     */
    public CityToCsv(final City aData) {
        data = aData;
    }

    /**
     * Transforms {@code City} instance to {@code StringBuilder}.
     *
     * @return {@code StringBuilder} instnce with data of th city transformed to
     * strings according to CSV specification.
     */
    @Override
    public final StringBuilder get() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(data.getId())
                .append(',')
                .append(escape(data.getName()))
                .append(',')
                .append(escape(data.getType()))
                .append(',')
                .append(data.getLatitude())
                .append(',')
                .append(data.getLongtitude())
                .append('\r')
                .append('\n');
        return builder;
    }

    /**
     * Escapes a value according to CSV specification. If a value contains
     * quotes, commas, carrige returns or line feeds, it is enclosed in quotes.
     * Alos, quotes are escaped with another quotes.
     *
     * @param aValue A value to be escaped.
     * @return Escaped value.
     */
    private static StringBuilder escape(final String aValue) {
        if (aValue != null) {
            if (ESCAPED_TEMPLATE.matcher(aValue).matches()) {
                StringBuilder escaped = new StringBuilder();
                escaped
                        .append('\"')
                        .append(QUOTES_TEMPLATE
                                .matcher(aValue).replaceAll("\"\""))
                        .append('\"');
                return escaped;
            } else {
                return new StringBuilder(aValue);
            }
        } else {
            return new StringBuilder();
        }
    }
}
