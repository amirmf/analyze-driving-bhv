package com.taraan.dum.dto.trip;

public class StartTripDto {
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "StartTripDto{" +
                "deviceId='" + deviceId + '\'' +
                '}';
    }
}
