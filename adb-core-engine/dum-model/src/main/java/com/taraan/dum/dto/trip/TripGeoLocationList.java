package com.taraan.dum.dto.trip;

import java.util.List;

public class TripGeoLocationList {
    private List<TripGeoLocation> geoLocations;

    public TripGeoLocationList() {
    }

    public TripGeoLocationList(List<TripGeoLocation> geoLocations) {
        this.geoLocations = geoLocations;
    }

    public List<TripGeoLocation> getGeoLocations() {
        return geoLocations;
    }

    public void setGeoLocations(List<TripGeoLocation> geoLocations) {
        this.geoLocations = geoLocations;
    }

    @Override
    public String toString() {
        return "TripGeoLocationList{" +
                "geoLocations=" + geoLocations +
                '}';
    }
}
