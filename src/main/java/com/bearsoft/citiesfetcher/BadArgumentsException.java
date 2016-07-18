package com.bearsoft.citiesfetcher;

/**
 * Exception class to be used for unparsable settings.
 *
 * @author mg
 */
public class BadArgumentsException extends Exception {

    /**
     * The exception default constructor.
     *
     * @param aMessage An error message.
     */
    public BadArgumentsException(final String aMessage) {
        super(aMessage);
    }
}
