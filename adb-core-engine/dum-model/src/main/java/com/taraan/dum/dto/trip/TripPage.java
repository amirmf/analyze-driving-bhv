package com.taraan.dum.dto.trip;

import java.util.ArrayList;
import java.util.List;

public class TripPage {
    private List<TripItemDto> trips = new ArrayList<>();
    private Long count;
    public TripPage(List<TripItemDto> trips, Long count) {
        this.trips = trips;
        this.count = count;
    }

    public TripPage() {
    }

    public List<TripItemDto> getTrips() {
        return trips;
    }

    public void setTrips(List<TripItemDto> trips) {
        this.trips = trips;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TripPage{" +
                "trips=" + trips +
                ", count=" + count +
                '}';
    }
}
