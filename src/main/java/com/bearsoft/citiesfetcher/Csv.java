package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.model.City;
import java.util.regex.Pattern;

/**
 * Transforms a {@code City} instance to CSV line. Every line ends with CRLF.
 * According to CSV specification, last line may end or mat not end with CRLF.
 *
 * @author mg
 */
public final class Csv {

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
     * Hidden constructor.
     */
    private Csv() {
    }

    /**
     * Transforms {@code City} instance to {@code StringBuilder}.
     *
     * @param aCity instance to be transformed to CSV line.
     * @return {@code StringBuilder} instance with data of th city transformed
     * to strings according to CSV specification.
     */
    public static StringBuilder to(final City aCity) {
        StringBuilder builder = new StringBuilder();
        builder
                .append(aCity.getId())
                .append(',')
                .append(escape(aCity.getName()))
                .append(',')
                .append(escape(aCity.getType()))
                .append(',')
                .append(aCity.getLatitude())
                .append(',')
                .append(aCity.getlongitude())
                .append('\r')
                .append('\n');
        return builder;
    }

    /**
     * Escapes a value according to CSV specification. If a value contains
     * quotes, commas, carriage returns or line feeds, it is enclosed in quotes.
     * Also, quotes are escaped with another quotes.
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
