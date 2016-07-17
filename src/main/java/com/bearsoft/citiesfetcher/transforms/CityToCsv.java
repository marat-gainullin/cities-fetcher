/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher.transforms;

import com.bearsoft.citiesfetcher.model.City;
import java.util.function.Supplier;

/**
 * Transforms a {@code City} instance to CSV line.
 *
 * @author mg
 */
public class CityToCsv implements Supplier<StringBuilder> {

    /**
     * {@code City} instance to be transformed to CSV line.
     *
     * @see City
     */
    private final City data;

    /**
     * City to CSV tranformer constructor.
     *
     * @param aData A {@code City} instance to be transformed.
     */
    public CityToCsv(final City aData) {
        data = aData;
    }

    /**
     * Transforms {@code City} instance to {@code StringBuilder}.
     *
     * @return {@code StringBuilder} instnce with data of th city transformed to
     * strings according to CSV specification.
     */
    @Override
    public final StringBuilder get() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(data.getId())
                .append(',')
                .append(data.getName())
                .append(',')
                .append(data.getType())
                .append(',')
                .append(data.getLatitude())
                .append(',')
                .append(data.getLongtitude());
        return builder;
    }

}
