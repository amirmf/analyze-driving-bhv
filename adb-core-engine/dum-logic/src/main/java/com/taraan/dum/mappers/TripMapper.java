package com.taraan.dum.mappers;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.dto.trip.TripDto;
import com.taraan.dum.dto.trip.TripItemDto;
import com.taraan.dum.model.hibernate.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripMapper {

    public static TripDto getDto(Trip trip) {
        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getId());
        if (trip.getTripStartDate() != null)
            tripDto.setTripStartDate(DateUtils.getDate(trip.getTripStartDate()));
        if (trip.getTripEndDate() != null)
            tripDto.setTripEndDate(DateUtils.getDate(trip.getTripEndDate()));
        tripDto.setName(trip.getName());
        tripDto.setUnderLimitedCount(trip.getSpeedDistributionUnderLimit());
        tripDto.setOverSpeedCount(trip.getSpeedDistributionOverSpeed());
        tripDto.setHighOverSpeedCount(trip.getSpeedDistributionHighOverSpeed());
        final double sum = trip.getSpeedDistributionUnderLimit() + trip.getSpeedDistributionOverSpeed() + trip.getSpeedDistributionHighOverSpeed();
        if (sum > 0) {
            tripDto.setUnderLimitedPercentage(Math.ceil((trip.getSpeedDistributionUnderLimit() * 100) / sum));
            tripDto.setOverSpeedCountPercentage((Math.ceil(trip.getSpeedDistributionOverSpeed() * 100) / sum));
            tripDto.setHighOverSpeedCountPercentage(100 - (tripDto.getOverSpeedCountPercentage() + tripDto.getUnderLimitedPercentage()));
        }

        tripDto.setAverageSpeed(trip.getAverageSpeed());
        tripDto.setMaxSpeed(trip.getMaxSpeed());
        tripDto.setCorneringCount(trip.getCorneringCount());
        tripDto.setBadBrakingCount(trip.getBrakingCount());//-2
        tripDto.setBadAccelerationCount(trip.getAccelerationCount());//+2
        tripDto.setDistance(trip.getDistance());//format
        tripDto.setOtherDistance(trip.getOtherDistance());
        tripDto.setUrbanDistance(trip.getUrbanDistance());
        tripDto.setHighwayDistance(trip.getHighwayDistance());
        tripDto.setIdleTime(trip.getIdleTime());//speed = 0
        tripDto.setDuration(trip.getDuration());//star trip - end trip
        tripDto.setBadDirection(trip.getBadDirection());
        tripDto.setMinIdleTime(trip.getMinIdleTime());
        tripDto.setMaxIdleTime(trip.getMaxIdleTime());
        tripDto.setMovementTime(trip.getMovementTime());
        tripDto.setMinMovementTime(trip.getMinMovementTime());
        tripDto.setMaxMovementTime(trip.getMaxMovementTime());
        return tripDto;
    }

    public static TripItemDto getSimpleDto(Trip trip) {
        TripItemDto tripDto = new TripItemDto();
        tripDto.setId(trip.getId());
        if (trip.getTripStartDate() != null)
            tripDto.setTripStartDate(DateUtils.getDate(trip.getTripStartDate()));
        if (trip.getTripEndDate() != null)
            tripDto.setTripEndDate(DateUtils.getDate(trip.getTripEndDate()));
        tripDto.setName(trip.getName());
        return tripDto;
    }

    public static List<TripItemDto> getDtos(List<Trip> tripes) {
        List<TripItemDto> result = new ArrayList<>();
        for (Trip trip : tripes) {
            result.add(getSimpleDto(trip));
        }
        return result;
    }
}
