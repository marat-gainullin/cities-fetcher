package com.bearsoft.citiesfetcher;

/**
 * Exception class to be used for unparsable settings. This class is used while
 * exception handling to inform a user about command line parameters. It is
 * checked exception to push a client code developer make an explicit handling.
 *
 * @author mg
 */
public class BadArgumentsException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -8612196963560955422L;

    /**
     * Constructs a exception with a message.
     *
     * @param aMessage An error message.
     */
    public BadArgumentsException(final String aMessage) {
        super(aMessage);
    }
}
