/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bearsoft.citiesfetcher.transforms;

import com.bearsoft.citiesfetcher.model.City;

/**
 * This builder class is intended to build {@code City} instances and check
 * thiers integrity.
 *
 * @author mg
 */
public final class CityBuilder {

    /**
     * City's id attribute.
     */
    private Long id;
    /**
     * City's name attribute.
     */
    private String name;
    /**
     * City's type attribute.
     */
    private String type;
    /**
     * City's latitude attribute.
     */
    private Double latitude;
    /**
     * City's longitude attribute.
     */
    private Double longitude;

    /**
     * Adds id attribute to the builder.
     *
     * @param aValue aValue of id attribute.
     * @return Underlying this {@code CityBuilder} instance.
     */
    public CityBuilder id(final long aValue) {
        id = aValue;
        return this;
    }

    /**
     * Adds name attribute to the builder.
     *
     * @param aValue aValue of name attribute.
     * @return Underlying this {@code CityBuilder} instance.
     */
    public CityBuilder name(final String aValue) {
        name = aValue;
        return this;
    }

    /**
     * Adds type attribute to the builder.
     *
     * @param aValue aValue of type attribute.
     * @return Underlying this {@code CityBuilder} instance.
     */
    public CityBuilder type(final String aValue) {
        type = aValue;
        return this;
    }

    /**
     * Adds latitude attribute to the builder.
     *
     * @param aValue aValue of latitude attribute.
     * @return Underlying this {@code CityBuilder} instance.
     */
    public CityBuilder latitude(final double aValue) {
        latitude = aValue;
        return this;
    }

    /**
     * Adds longitude attribute to the builder.
     *
     * @param aValue aValue of longitude attribute.
     * @return Underlying this {@code CityBuilder} instance.
     */
    public CityBuilder longitude(final double aValue) {
        longitude = aValue;
        return this;
    }

    public City toCity() throws PartialCityJsonException {
        if (id == null) {
            throw new PartialCityJsonException("id");
        }
        if (name == null) {
            throw new PartialCityJsonException("name");
        }
        if (latitude == null) {
            throw new PartialCityJsonException("latitude");
        }
        if (longitude == null) {
            throw new PartialCityJsonException("longitude");
        }
        return new City(id, name, type, latitude, longitude);
    }
}
