package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.trip.*;
import com.taraan.dum.logic.TripLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class TripAssembler {
    @Autowired
    private TripLogic tripLogic;

    public Long startTrip(StartTripDto startTripDto, Long userId) {
        return tripLogic.startTrip(startTripDto, userId);
    }

    public boolean endTrip(EndTripDto endTripDto) {
        return tripLogic.endTrip(endTripDto, new Date());
    }

    public TripPage getTrips(Long userId, Integer pageNumber) {
        int from = pageNumber * 10;
        int size = from + 10;
        return tripLogic.getTrips(userId, from, size);
    }

    public TripDto getTrip(Long userId, Long id) {
        return tripLogic.getTrip(userId, id);
    }

    public TripDto getTrip(Long userId) {
        return tripLogic.getTrip(userId);
    }

    public List<TripGeoLocation> getTripGeoLocations(Long userId, Long id) {
        return tripLogic.getTripGeoLocations(userId, id);
    }

    public List<TripGeoLocation> getTripGeoLocations(Long id) {
        return tripLogic.getTripGeoLocations(id);
    }

    public Boolean isActiveTrip(Long userId) {
        return tripLogic.isActiveTrip(userId);
    }

    public void removeTrip(Long userId, Long id) {
        tripLogic.removeTrip(userId, id);
    }

    public TripPage getTrips(String phoneNumber, String fromStartDate, String toStartDate, Double fromDistance, Double toDistance, Double fromDuration, Double toDuration, Long from, Long size) {
        return tripLogic.getTrips(phoneNumber, fromStartDate, toStartDate, fromDistance, toDistance, fromDuration, toDuration, from, size);
    }
}
