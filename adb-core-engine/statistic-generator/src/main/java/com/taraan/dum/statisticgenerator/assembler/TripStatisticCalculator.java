package com.taraan.dum.statisticgenerator.assembler;

import com.taraan.dum.audit.mongodb.RoadType;
import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import com.taraan.dum.dto.trip.TripInfoDto;
import com.taraan.dum.dto.trip.TripOfHourDto;
import com.taraan.dum.statisticgenerator.dto.TempTripInfo;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static com.taraan.dum.audit.mongodb.RoadType.HIGHWAY;
import static com.taraan.dum.audit.mongodb.RoadType.OTHER;
import static com.taraan.dum.audit.mongodb.RoadType.URBAN;

@Component
public class TripStatisticCalculator {

    public int getHour(Date postDate) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(postDate);
        return instance.get(Calendar.HOUR_OF_DAY);
    }

    public TripOfHourDto getTripHourStatistic(Integer hour, TempTripInfo tempTripInfo) {
        TripOfHourDto tripOfHourDto = new TripOfHourDto();
        tripOfHourDto.setHour(hour);
        tripOfHourDto.setDistance(tempTripInfo.getHourTripDistance());
        tripOfHourDto.setTime(tempTripInfo.getHourTripTime() / 1000);
        tempTripInfo.setHourTripDistance(0D);
        tempTripInfo.setHourTripTime(0D);
        return tripOfHourDto;
    }

    public void calculateSpeedDistribution(TripInfoDto trip, TripTrackerInfo tripTrackerInfo, double realSpeed) {
        double validSpeed = getValidSpeed(tripTrackerInfo);
        if (realSpeed > validSpeed) {
            final double difference = realSpeed - validSpeed;
            double rate = (difference * 100) / validSpeed;
            if (rate >= 20) {
                trip.setSpeedDistributionHighOverSpeed(trip.getSpeedDistributionHighOverSpeed() + 1);
            } else {
                trip.setSpeedDistributionOverSpeed(trip.getSpeedDistributionOverSpeed() + 1);
            }
        } else {
            trip.setSpeedDistributionUnderLimit(trip.getSpeedDistributionUnderLimit() + 1);
        }
    }

    private double getValidSpeed(TripTrackerInfo tripTrackerInfo) {
        //todo get valid speed;
        return 90;
    }

    public boolean isNotValidGeoLocation(double latitude, double longitude) {
        return latitude == 0 && longitude == 0;
    }

    public void setRoadTypeDistance(TripInfoDto trip, TripTrackerInfo tripTrackerInfo, double currentDistance) {
        switch (getRoadType(tripTrackerInfo)) {
            case URBAN: {
                trip.setHighwayDistance(trip.getUrbanDistance() + currentDistance);
                break;
            }
            case HIGHWAY: {
                trip.setHighwayDistance(trip.getHighwayDistance() + currentDistance);
                break;
            }
            case OTHER: {
                trip.setOtherDistance(trip.getOtherDistance() + currentDistance);
                break;
            }
        }
    }

    private RoadType getRoadType(TripTrackerInfo tripTrackerInfo) {
        //todo check it
        return RoadType.OTHER;
    }

    public void calculateIdleAndMovementTime(TripInfoDto trip, TempTripInfo tempTripInfo, TripTrackerInfo previousItem, TripTrackerInfo item, Long deltaT) {
        double deltaV = 0d;
        if (previousItem != null)
            deltaV = (item.getRealSpeed() - previousItem.getRealSpeed()) / deltaT;
        if (deltaV == 0) {
            if (tempTripInfo.getTempIdleTime() > 15000) {
                trip.setMovementTime(trip.getMovementTime() + tempTripInfo.getTempMovementTime());
                if (tempTripInfo.getTempMovementTime() > trip.getMaxMovementTime()) {
                    trip.setMaxMovementTime(tempTripInfo.getTempMovementTime());
                }
                if (trip.getMinMovementTime() == 0 || tempTripInfo.getTempMovementTime() < trip.getMinMovementTime()) {
                    trip.setMinMovementTime(tempTripInfo.getTempMovementTime());
                }
                tempTripInfo.setTempMovementTime(0L);
            }
            if (previousItem != null)
                tempTripInfo.setTempIdleTime(tempTripInfo.getTempIdleTime() + (deltaT));
        } else {
            if (tempTripInfo.getTempIdleTime() > 15000) {
                trip.setIdleTime(trip.getIdleTime() + tempTripInfo.getTempIdleTime());
                if (tempTripInfo.getTempIdleTime() > trip.getMaxIdleTime()) {
                    trip.setMaxIdleTime(tempTripInfo.getTempIdleTime());
                }
                if (trip.getMinIdleTime() == 0 || tempTripInfo.getTempIdleTime() < trip.getMinIdleTime()) {
                    trip.setMinIdleTime(tempTripInfo.getTempIdleTime());
                }
            } else if (tempTripInfo.getTempIdleTime() != 0) {
                tempTripInfo.setTempMovementTime(tempTripInfo.getTempMovementTime() + tempTripInfo.getTempIdleTime());
            }
            if (previousItem != null)
                tempTripInfo.setTempMovementTime(tempTripInfo.getTempMovementTime() + (deltaT));
            tempTripInfo.setTempIdleTime(0L);
        }
    }

    public void calculateLatestIdleAndMovementTime(TripInfoDto trip, TempTripInfo tempTripInfo) {
        trip.setMovementTime(trip.getMovementTime() + tempTripInfo.getTempMovementTime());
        if (tempTripInfo.getTempMovementTime() > trip.getMaxMovementTime()) {
            trip.setMaxMovementTime(tempTripInfo.getTempMovementTime());
        }
        if (trip.getMinMovementTime() == 0 || tempTripInfo.getTempMovementTime() < trip.getMinMovementTime()) {
            trip.setMinMovementTime(tempTripInfo.getTempMovementTime());
        }
        if (tempTripInfo.getTempIdleTime() > 200000) {
            trip.setIdleTime(trip.getIdleTime() + tempTripInfo.getTempIdleTime());
            if (tempTripInfo.getTempIdleTime() > trip.getMaxIdleTime()) {
                trip.setMaxIdleTime(tempTripInfo.getTempIdleTime());
            }
            if (trip.getMinIdleTime() == 0 || tempTripInfo.getTempIdleTime() < trip.getMinIdleTime()) {
                trip.setMinIdleTime(tempTripInfo.getTempIdleTime());
            }
        }
    }

    public int setAccelerationsCount(TripInfoDto trip, double currentRealSpeed, double preRealSpeed, Long deltaT) {
        if (currentRealSpeed <= 0)
            return 0;
        float ratio = 34000f;
        final double deltaV = currentRealSpeed - preRealSpeed;

        final double a = deltaV / deltaT;
        if (a > 0) {
            if (a * 3600 > ratio) {
                trip.setAccelerationCount(trip.getAccelerationCount() + 1);
                return 1;
            }

        } else if (a < 0) {
            if ((-1 * deltaV) * 3600 > ratio) {
                trip.setBrakingCount(trip.getAccelerationCount() + 1);
                return -1;
            }
        }
        return 0;
    }
/*
    private double getAcceleration(TripTrackerInfo tripTrackerInfo) {
        double acceleration;
        if (Math.abs(tripTrackerInfo.getX()) > Math.abs(tripTrackerInfo.getY()))
            acceleration = tripTrackerInfo.getX();
        else
            acceleration = tripTrackerInfo.getY();

        if (Math.abs(tripTrackerInfo.getZ()) > Math.abs(acceleration))
            acceleration = tripTrackerInfo.getZ();
        return acceleration;
    }*/
/*
    public void speedAgregator(TripInfoDto trip, List<TripTrackerInfo> tripTrackerInfoList) {

        DoubleSummaryStatistics speedSummaryStatistics = tripTrackerInfoList.stream().mapToDouble(TripTrackerInfo::getRealSpeed).summaryStatistics();
        trip.setAverageSpeed(speedSummaryStatistics.getAverage());
        trip.setMaxSpeed(speedSummaryStatistics.getMax());
    }*/

    public void setSpeedDistributionPercentage(TripInfoDto trip) {
        double sum = trip.getSpeedDistributionUnderLimit() + trip.getSpeedDistributionOverSpeed() + trip.getSpeedDistributionHighOverSpeed();
        if (sum != 0) {
            trip.setSpeedDistributionUnderLimit(trip.getSpeedDistributionUnderLimit() * 100 / sum);
            trip.setSpeedDistributionOverSpeed(trip.getSpeedDistributionOverSpeed() * 100 / sum);
            trip.setSpeedDistributionHighOverSpeed(trip.getSpeedDistributionHighOverSpeed() * 100 / sum);
        }
    }
}
