/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher.feed;

import com.bearsoft.citiesfetcher.model.City;
import com.bearsoft.citiesfetcher.feed.BadCitiesJsonException;
import com.bearsoft.citiesfetcher.feed.PartialCityJsonException;
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
     * @throws IOException if any IO problem or Json problem occurs.
     * @throws PartialCityJsonException if some mandatory field of {@code City}
     * are missing in Json tokens stream.
     * @throws BadCitiesJsonException if some bad structure discovered while
     * parsing process.
     */
    Optional<City> pull() throws IOException,
            PartialCityJsonException,
            BadCitiesJsonException;
}
