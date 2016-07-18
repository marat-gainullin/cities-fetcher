package com.bearsoft.citiesfetcher.model;

/**
 * This exception is intended to be thrown by reader code when it discovers a
 * mandatory fields absence in input Json.
 *
 * @author mg
 */
public final class PartialCityJsonException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -8159825484237940759L;

    /**
     * Constructor of the exception with absent name information.
     *
     * @param aFieldName Name of the absent field.
     */
    public PartialCityJsonException(final String aFieldName) {
        super(String.format("Field %s is absent in city json.", aFieldName));
    }
}
