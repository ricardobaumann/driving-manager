package com.driving.domainvalue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GeoCoordinate {

    private static final int MAX_LATITUDE = 90;
    private static final int MIN_LATITUDE = -90;
    private static final int MAX_LONGITUDE = 180;
    private static final int MIN_LONGITUDE = -180;
    @Column(name = "coordinate")
    private final Point point;


    protected GeoCoordinate() {
        point = null;
    }


    /**
     * @param latitude  - y coordinate
     * @param longitude - x coordinate
     */
    public GeoCoordinate(double latitude, double longitude) {
        Preconditions.checkArgument(latitude >= MIN_LATITUDE, "latitude is lower than min_latitude: " + MIN_LATITUDE);
        Preconditions.checkArgument(latitude <= MAX_LATITUDE, "latitude is higher than max_latitude: " + MAX_LATITUDE);
        Preconditions.checkArgument(longitude >= MIN_LONGITUDE, "longitude is lower than min_longitude: " + MIN_LONGITUDE);
        Preconditions.checkArgument(longitude <= MAX_LONGITUDE, "longitude is higher than max_longitude: " + MAX_LONGITUDE);

        point = new Point(longitude, latitude);
    }


    @JsonProperty
    public double getLatitude() {
        return point.getY();
    }


    @JsonIgnore
    public Point getPoint() {
        return point;
    }


    @JsonProperty
    public double getLongitude() {
        return point.getX();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((point == null) ? 0 : point.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GeoCoordinate other = (GeoCoordinate) obj;
        if (point == null) {
            if (other.point != null) {
                return false;
            }
        } else if (!point.equals(other.point)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return point.toString();
    }

}
