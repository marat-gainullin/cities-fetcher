/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

/**
 * Exception class to be used for unparsable settings.
 *
 * @author mg
 */
public class BadSettingsFormatException extends Exception {

    /**
     * The exception default constructor.
     *
     * @param aMessage An error message.
     */
    public BadSettingsFormatException(final String aMessage) {
        super(aMessage);
    }
}
