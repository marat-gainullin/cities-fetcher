package com.bearsoft.citiesfetcher.feed;

/**
 * This exception is thrown when {@code CitiesFeed} discovers some bad sequence
 * of object starts/ends or Json array starts/ends.
 *
 * @author mg
 */
public class BadCitiesJsonException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1692705561481746455L;

    /**
     * Constructs a exception with the specified message.
     *
     * @param aMessage An error message.
     */
    public BadCitiesJsonException(final String aMessage) {
        super(aMessage);
    }
}
