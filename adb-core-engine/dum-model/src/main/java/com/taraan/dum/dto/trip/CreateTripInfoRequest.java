package com.taraan.dum.dto.trip;

import java.util.Date;

public class CreateTripInfoRequest {
    private String deviceId;
    private Long tripId;
    private Date fromDate;
    private Date toDate;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "CreateTripInfoRequest{" +
                "deviceId='" + deviceId + '\'' +
                ", tripId=" + tripId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
