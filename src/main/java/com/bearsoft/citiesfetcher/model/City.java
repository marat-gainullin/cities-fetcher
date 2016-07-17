package com.bearsoft.citiesfetcher.model;

/**
 * This is a model class. Instances of this class can be read or written to or
 * from various sources or destinations. For example an inastcne of {@code City}
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
     * Latitude of the city. Citites can be searched by this property.
     */
    private final double latitude;
    /**
     * longitude of the city. Citites can be searched by this property.
     */
    private final double longitude;

    /**
     * Constructor of {@code City} with all attributes.
     *
     * @param aId A city's identifier.
     * @param aName A city's name.
     * @param aType A city's type.
     * @param aLatitude A city's laitude.
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
     * City's lontitude getter.
     *
     * @return City's lontitude.
     */
    public double getlongitude() {
        return longitude;
    }

}
