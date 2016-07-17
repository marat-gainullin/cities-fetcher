/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher;

import com.bearsoft.citiesfetcher.model.City;
import java.io.IOException;
import java.util.Optional;

/**
 * Interface intended for using with feeds of cities, been read from some IO
 * streams.
 *
 * @author mg
 */
public interface CitiesFeed {

    /**
     * Pulls some data from source and reads a {@code City} instance.
     *
     * @return {@code City} instance pulled from source.
     * @throws IOException
     */
    public Optional<City> pull() throws IOException;
}
