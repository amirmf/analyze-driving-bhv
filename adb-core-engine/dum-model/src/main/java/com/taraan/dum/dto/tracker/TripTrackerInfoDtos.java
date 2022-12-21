package com.taraan.dum.dto.tracker;

import java.util.ArrayList;
import java.util.List;

public class TripTrackerInfoDtos {
    List<TripTrackerInfoDto> tripTrackerInfos = new ArrayList<>();

    public List<TripTrackerInfoDto> getTripTrackerInfos() {
        return tripTrackerInfos;
    }

    public void setTripTrackerInfos(List<TripTrackerInfoDto> tripTrackerInfos) {
        this.tripTrackerInfos = tripTrackerInfos;
    }

    @Override
    public String toString() {
        return "TripTrackerInfoDtos{" +
                "tripTrackerInfos=" + tripTrackerInfos +
                '}';
    }
}
