package com.bearsoft.citiesfetcher.model;

/**
 * This is a model class. Instances of this class can be read or written to
 * various sources or destinations. For example an inastcneof {@code City} can
 * be red from JSON and it can be written to CSV. This class is not for
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
     * Longtitude of the city. Citites can be searched by this property.
     */
    private final double longtitude;

    /**
     * Constructor of {@code City} with all attributes.
     *
     * @param aId
     * @param aName
     * @param aType
     * @param aLatitude
     * @param aLongtitude
     */
    public City(long aId, String aName, String aType, double aLatitude, double aLongtitude) {
        id = aId;
        name = aName;
        type = aType;
        latitude = aLatitude;
        longtitude = aLongtitude;
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
    public double getLongtitude() {
        return longtitude;
    }

}
