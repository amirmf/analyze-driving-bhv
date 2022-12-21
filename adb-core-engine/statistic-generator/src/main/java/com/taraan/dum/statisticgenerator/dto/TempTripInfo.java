package com.taraan.dum.statisticgenerator.dto;

import com.taraan.dum.dto.trip.TripOfHourDto;

import java.util.ArrayList;
import java.util.List;

public class TempTripInfo {
    private Long tempIdleTime = 0L;
    private Long tempMovementTime = 0L;
    private Double hourTripDistance = 0D;
    private Double hourTripTime = 0D;
    List<TripOfHourDto> tripOfHours = new ArrayList<>();

    public Long getTempIdleTime() {
        return tempIdleTime;
    }

    public void setTempIdleTime(Long tempIdleTime) {
        this.tempIdleTime = tempIdleTime;
    }

    public Long getTempMovementTime() {
        return tempMovementTime;
    }

    public void setTempMovementTime(Long tempMovementTime) {
        this.tempMovementTime = tempMovementTime;
    }

    public Double getHourTripDistance() {
        return hourTripDistance;
    }

    public void setHourTripDistance(Double hourTripDistance) {
        this.hourTripDistance = hourTripDistance;
    }

    public Double getHourTripTime() {
        return hourTripTime;
    }

    public void setHourTripTime(Double hourTripTime) {
        this.hourTripTime = hourTripTime;
    }

    public List<TripOfHourDto> getTripOfHours() {
        return tripOfHours;
    }

    public void setTripOfHours(List<TripOfHourDto> tripOfHours) {
        this.tripOfHours = tripOfHours;
    }
}
