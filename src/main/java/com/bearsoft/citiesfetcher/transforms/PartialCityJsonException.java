package com.bearsoft.citiesfetcher.transforms;

import java.io.IOException;

/**
 * This excception is intended to be thrown by reader code when it discovers a
 * mandatory fields absence in input json.
 *
 * @author mg
 */
public final class PartialCityJsonException extends IOException {

    /**
     * Constructor of the exception with absent name information.
     * @param aFieldName Name of the absent field.
     */
    public PartialCityJsonException(final String aFieldName) {
        super("Field " + aFieldName + " is absent in city json.");
    }
}
