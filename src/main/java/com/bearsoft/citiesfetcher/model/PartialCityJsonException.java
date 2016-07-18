package com.bearsoft.citiesfetcher.model;

/**
 * This exception is intended to be thrown by reader code when it discovers a
 * mandatory fields absence in input json.
 *
 * @author mg
 */
public final class PartialCityJsonException extends Exception {

    /**
     * Constructor of the exception with absent name information.
     *
     * @param aFieldName Name of the absent field.
     */
    public PartialCityJsonException(final String aFieldName) {
        super(String.format("Field %s is absent in city json.", aFieldName));
    }
}
