/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher.feed;

/**
 * This exception is thrown when {@code CitiesFeed} discovers some bad sequence
 * of object starts/ends or json array starts/ends.
 *
 * @author mg
 */
public class BadCitiesJsonException extends Exception {

    /**
     * Constructs a exception with the specified message.
     * @param aMessage An error message.
     */
    public BadCitiesJsonException(final String aMessage){
        super(aMessage);
    }
}
