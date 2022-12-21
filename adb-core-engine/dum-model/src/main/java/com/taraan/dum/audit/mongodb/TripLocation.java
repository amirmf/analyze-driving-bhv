package com.taraan.dum.audit.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TripLocation")
public class TripLocation {
    @Id
    private String _id;
    private Long tripId;
    private double longitude;
    private double latitude;
    private Integer event;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Integer getEvent() {
        return event;
    }
}
