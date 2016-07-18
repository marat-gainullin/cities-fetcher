package com.bearsoft.citiesfetcher.model;

/**
 * This is a model class. Instances of this class can be read or written to or
 * from various sources or destinations. For example an instance of {@code City}
 * can be red from JSON and it can be written to CSV. This class is not for
 * modification and so it has only final fields. Also it is not intended for
 * extension and so it is also final.
 *
 * @author mg
 */
public final class City {

    /**
     * The key of the city.
     */
    private final long id;
    /**
     * City's name.
     */
    private final String name;
    /**
     * Type of city's identification.
     */
    private final String type;
    /**
     * Latitude of the city. Cities can be searched by this property.
     */
    private final double latitude;
    /**
     * longitude of the city. Cities can be searched by this property.
     */
    private final double longitude;

    /**
     * Constructor of {@code City} with all attributes.
     *
     * @param aId A city's identifier.
     * @param aName A city's name.
     * @param aType A city's type.
     * @param aLatitude A city's latitude.
     * @param alongitude A city's longitude.
     */
    public City(final long aId, final String aName, final String aType,
            final double aLatitude, final double alongitude) {
        id = aId;
        name = aName;
        type = aType;
        latitude = aLatitude;
        longitude = alongitude;
    }

    /**
     * City's id getter.
     *
     * @return City's id.
     */
    public long getId() {
        return id;
    }

    /**
     * City's name getter.
     *
     * @return City's name.
     */
    public String getName() {
        return name;
    }

    /**
     * City's type getter.
     *
     * @return City's type.
     */
    public String getType() {
        return type;
    }

    /**
     * City's latitude getter.
     *
     * @return City's latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * City's longitude getter.
     *
     * @return City's longitude.
     */
    public double getlongitude() {
        return longitude;
    }

    /**
     * This builder class is intended to build {@code City} instances and check
     * theirs integrity. If it discovers, that some of mandatory fields are
     * missing, exception will be thrown.
     *
     * @author mg
     */
    public static final class Builder {

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
        public Builder id(final long aValue) {
            id = aValue;
            return this;
        }

        /**
         * Adds name attribute to the builder.
         *
         * @param aValue aValue of name attribute.
         * @return Underlying this {@code CityBuilder} instance.
         */
        public Builder name(final String aValue) {
            name = aValue;
            return this;
        }

        /**
         * Adds type attribute to the builder.
         *
         * @param aValue aValue of type attribute.
         * @return Underlying this {@code CityBuilder} instance.
         */
        public Builder type(final String aValue) {
            type = aValue;
            return this;
        }

        /**
         * Adds latitude attribute to the builder.
         *
         * @param aValue aValue of latitude attribute.
         * @return Underlying this {@code CityBuilder} instance.
         */
        public Builder latitude(final double aValue) {
            latitude = aValue;
            return this;
        }

        /**
         * Adds longitude attribute to the builder.
         *
         * @param aValue aValue of longitude attribute.
         * @return Underlying this {@code CityBuilder} instance.
         */
        public Builder longitude(final double aValue) {
            longitude = aValue;
            return this;
        }

        /**
         * Build a {@code City} instance with accumulated data.
         *
         * @return {@code City} instance filled with accumulated data.
         * @throws PartialCityJsonException If some part of mandatory data is
         * absent.
         */
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

}
