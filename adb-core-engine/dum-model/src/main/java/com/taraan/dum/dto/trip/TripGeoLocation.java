package com.taraan.dum.dto.trip;

public class TripGeoLocation {
    private Double longitude;
    private Double latitude;
    private Integer event;

    public TripGeoLocation() {
    }

    public TripGeoLocation(Double longitude, Double latitude,Integer event) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.event = event;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "TripGeoLocation{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
