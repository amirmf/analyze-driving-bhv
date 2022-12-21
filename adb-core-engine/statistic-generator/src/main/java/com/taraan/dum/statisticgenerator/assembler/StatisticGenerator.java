package com.taraan.dum.statisticgenerator.assembler;

import com.taraan.dum.audit.mongodb.RoadType;
import com.taraan.dum.audit.mongodb.TripLocation;
import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import com.taraan.dum.da.DailyTripInfoDa;
import com.taraan.dum.da.TripDa;
import com.taraan.dum.da.UserDa;
import com.taraan.dum.dto.analyze.JmsDailyAnalyzeRequest;
import com.taraan.dum.dto.trip.CreateTripInfoRequest;
import com.taraan.dum.dto.trip.TripInfoDto;
import com.taraan.dum.dto.trip.TripOfHourDto;
import com.taraan.dum.model.hibernate.DailyTripInfo;
import com.taraan.dum.model.hibernate.StatisticOfHour;
import com.taraan.dum.model.hibernate.Trip;
import com.taraan.dum.model.hibernate.TripOfHour;
import com.taraan.dum.statisticgenerator.Comparators;
import com.taraan.dum.statisticgenerator.dto.TempTripInfo;
import com.taraan.dum.statisticgenerator.mongodb.TripLocationRepository;
import com.taraan.dum.statisticgenerator.mongodb.TripTrackerInfoRepository;
import com.taraan.dum.statisticgenerator.utils.DistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Transactional
public class StatisticGenerator {
    @Autowired
    private TripTrackerInfoRepository tripTrackerInfoRepository;
    @Autowired
    private TripLocationRepository tripLocationRepository;
    @Autowired
    private UserDa userDa;
    @Autowired
    private TripDa tripDa;
    @Autowired
    private DailyTripInfoDa statisticDa;
    @Autowired
    private TripStatisticCalculator tripStatisticCalculator;
    @Autowired
    private MessageProducer messageProducer;

    public TripInfoDto createTripStatistic(CreateTripInfoRequest request) {
        TripInfoDto trip = new TripInfoDto();
        TempTripInfo tempTripInfo = new TempTripInfo();
        List<TripTrackerInfo> tripTrackerInfoList = getTripTrackerInfos(request);
        TripTrackerInfo previousItem = null;
        if (tripTrackerInfoList.isEmpty())
            return trip;
//        if (tripTrackerInfoList.get(0).getOrder() != 1)
//            return trip;

//        if (!isSequence(tripTrackerInfoList))
//            return trip;
//        hasDuplicateOrder(tripTrackerInfoList);
//        if (hasDuplicateOrder(tripTrackerInfoList))
//            return trip;
        Map<Long, Integer> accelerationMaps = new HashMap<>();
        //initialize hour with start trip
        Integer hour = tripStatisticCalculator.getHour(tripTrackerInfoList.get(0).getPostDate());
        for (TripTrackerInfo item : tripTrackerInfoList) {
            if (tripStatisticCalculator.isNotValidGeoLocation(item.getLatitude(), item.getLongitude()))
                continue;
            if (item.getRealSpeed() > 200 || item.getRealSpeed() < 0)
                continue;
            Long deltaT = 1L;
            if (previousItem != null) {
                deltaT = item.getUserPostDateTimeStamp() - previousItem.getUserPostDateTimeStamp();
                if (previousItem.getOrder() == item.getOrder()) {
                    continue;

//                    if (previousItem.getUserPostDateTimeStamp() == item.getUserPostDateTimeStamp())
//                    else
//                        System.out.println();
                }
                if (previousItem.getUserPostDateTimeStamp() >= item.getUserPostDateTimeStamp()) {
                    continue;
                }


//                if ((item.getOrder() - previousItem.getOrder() != 1)) {
//                    previousItem = item;
//                    continue;
//                }

            } else {
                previousItem = item;
                continue;
            }


            if (tripStatisticCalculator.getHour(item.getPostDate()) != hour) {
                tempTripInfo.getTripOfHours().add(tripStatisticCalculator.getTripHourStatistic(hour, tempTripInfo));
                hour = tripStatisticCalculator.getHour(item.getPostDate());
            }

            double latestDistance = DistanceUtils.distFrom(previousItem.getLatitude(), previousItem.getLongitude(),
                    item.getLatitude(), item.getLongitude());
            if (latestDistance != Double.NaN && latestDistance != 0) {
                final double realSpeed = calculateSpeed(item, previousItem, deltaT);
                if (realSpeed > 0) {
                    if (trip.getMaxSpeed() < realSpeed)
                        trip.setMaxSpeed(realSpeed);
                    tripStatisticCalculator.calculateSpeedDistribution(trip, item, realSpeed);

                    final int i = tripStatisticCalculator.setAccelerationsCount(trip, realSpeed, previousItem.getRealSpeed(), deltaT);
                    if (i != 0) {
                        accelerationMaps.put(item.getOrder(), i);
                    }
                }
                trip.setDistance(trip.getDistance() + latestDistance);
                tempTripInfo.setHourTripDistance(tempTripInfo.getHourTripDistance() + latestDistance);
                tempTripInfo.setHourTripTime(tempTripInfo.getHourTripTime() + (item.getPostDate().getTime() - previousItem.getPostDate().getTime()));
                tripStatisticCalculator.setRoadTypeDistance(trip, item, latestDistance);
            }

            /* //todo how to get valid direction
            if (tripTrackerInfo.getDirection() != tripTrackerInfo.getValidDirection()) {
                badDirectionCount++;
            }*/
            tripStatisticCalculator.calculateIdleAndMovementTime(trip, tempTripInfo, previousItem, item, deltaT);

//                corneringCount++; todo must be calculate

            previousItem = item;
        }
        if (trip.getMaxSpeed() > 145) {
            trip.setMaxSpeed(145);
        }
        tempTripInfo.getTripOfHours().add(tripStatisticCalculator.getTripHourStatistic(hour, tempTripInfo));
        tripStatisticCalculator.calculateLatestIdleAndMovementTime(trip, tempTripInfo);
        trip.setTripOfHours(tempTripInfo.getTripOfHours().toArray(new TripOfHourDto[]{}));
        tripStatisticCalculator.setSpeedDistributionPercentage(trip);
//        trip.setCorneringCount(corneringCount);//todo
//        trip.setBadDirection(badDirectionCount);//todo
        trip.setIdleTime(trip.getIdleTime() / 1000);
        trip.setMinIdleTime(trip.getMinIdleTime() / 1000);
        trip.setMovementTime(trip.getMovementTime() / 1000);
        trip.setMaxMovementTime(trip.getMaxMovementTime() / 1000);
        trip.setMinMovementTime(trip.getMinMovementTime() / 1000);
        trip.setMaxIdleTime(trip.getMaxIdleTime() / 1000);
        trip.setWeekend(isWeekend());

        final double realAverageSpeed = tripTrackerInfoList.stream().mapToDouble(TripTrackerInfo::getRealSpeed).summaryStatistics().getAverage();
        if (trip.getMovementTime() != 0)
            trip.setAverageSpeed((trip.getDistance() / (Math.abs(trip.getMovementTime())) * 3600));
        final double vRatio = Math.abs(trip.getAverageSpeed() - realAverageSpeed) / trip.getAverageSpeed();
        if (vRatio < 0.8 || vRatio > 1.2)
            trip.setAverageSpeed(realAverageSpeed);
        if (trip.getMaxSpeed() <= trip.getAverageSpeed() || (trip.getAverageSpeed() <= 0)) {
            trip.setAverageSpeed(trip.getMaxSpeed() * 0.9);
        }
        saveLocations(request, tripTrackerInfoList, accelerationMaps);
        return trip;
    }

    private boolean isSequence(List<TripTrackerInfo> tripTrackerInfoList) {
        return IntStream.range(0, tripTrackerInfoList.size() - 1)
                .allMatch(i -> tripTrackerInfoList.get(i + 1).getOrder() - tripTrackerInfoList.get(i).getOrder() < 20);
    }

    private boolean hasDuplicateOrder(List<TripTrackerInfo> tripTrackerInfoList) {
        final Map<Long, Long> orderMaps = tripTrackerInfoList.stream().collect(Collectors.groupingBy(
                TripTrackerInfo::getOrder,
                Collectors.counting()
        ));
        final HashSet<Long> orderCounts = new HashSet<>(orderMaps.values());
        for (Long order : orderCounts) {
            if (order != 1)
                return true;
        }
        return false;
    }

    private boolean isWeekend() {
        final Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        if (day_of_week == Calendar.FRIDAY || day_of_week == Calendar.THURSDAY)
            return true;
        return false;
    }

    private double calculateSpeed(TripTrackerInfo item, TripTrackerInfo previousItem, Long deltaT) {
        if (item.getOrder() > 15) {
            if (item.getRealSpeed() < 100)
                return item.getRealSpeed();
            else {
                if (previousItem.getRealSpeed() > item.getRealSpeed())
                    return item.getRealSpeed();

                final double v = item.getRealSpeed() / previousItem.getRealSpeed();
                if ((v > 0.5 * deltaT)) {
                    return previousItem.getRealSpeed();
                } else {
                    return item.getRealSpeed();
                }
            }
        }
        return 0;
    }

    private double calculateSpeed(double latestDistance) {
        return latestDistance * 3600;
    }


    private List<TripTrackerInfo> getTripTrackerInfos(CreateTripInfoRequest request) {
        List<TripTrackerInfo> tripTrackerInfoList = tripTrackerInfoRepository.getTripTrackerInfoeList(request.getDeviceId(), request.getFromDate(), request.getToDate());
        tripTrackerInfoList.sort(Comparators.TRIP_TRACKER_INFO_COMPARATOR);
        return tripTrackerInfoList;
    }


    private void saveLocations(CreateTripInfoRequest request, List<TripTrackerInfo> tripTrackerInfoList, Map<Long, Integer> accelerationMaps) {
        List<TripLocation> tripLocations = new ArrayList<>();
        for (TripTrackerInfo trackerInfo : tripTrackerInfoList) {
            if (tripStatisticCalculator.isNotValidGeoLocation(trackerInfo.getLatitude(), trackerInfo.getLongitude()))
                continue;
            TripLocation tripLocation = new TripLocation();
            final Integer event = accelerationMaps.get(trackerInfo.getOrder());
            if (event != null) {
                tripLocation.setEvent(event);
            }
            tripLocation.setLatitude(trackerInfo.getLatitude());
            tripLocation.setLongitude(trackerInfo.getLongitude());
            tripLocation.setTripId(request.getTripId());
            tripLocations.add(tripLocation);
        }
        tripLocationRepository.save(tripLocations);
    }


    public void createDailyStatistics(Long userId, Date from, Date to) {
        List<Trip> trips = tripDa.gets(userId, from, to);
        DailyTripInfo statistic = new DailyTripInfo();
        Map<Integer, StatisticOfHour> hourMap = new HashMap<>();
        statistic.setDuration(0L);
        statistic.setIdleTime(0L);
        statistic.setMovementTime(0L);

        for (Trip trip : trips) {
            statistic.setCorneringCount(statistic.getCorneringCount() + trip.getCorneringCount());
            statistic.setBrakingCount(statistic.getBrakingCount() + trip.getBrakingCount());
            statistic.setAccelerationCount(statistic.getAccelerationCount() + trip.getAccelerationCount());
            statistic.setDistance(statistic.getDistance() + trip.getDistance());
            statistic.setDuration(statistic.getDuration() + trip.getDuration());
            statistic.setIdleTime(statistic.getIdleTime() + trip.getIdleTime());
            statistic.setMovementTime(statistic.getMovementTime() + trip.getMovementTime());
            statistic.setUrbanDuration(statistic.getUrbanDuration() + trip.getUrbanDistance());
            statistic.setHighWayDuration(statistic.getHighWayDuration() + trip.getHighwayDistance());
            statistic.setOtherRoadDuration(statistic.getOtherRoadDuration() + trip.getOtherDistance());
            if (statistic.getMaxIdleTime() == null || statistic.getMaxIdleTime() < trip.getMaxIdleTime()) {
                statistic.setMaxIdleTime(trip.getMaxIdleTime());
            }
            if (statistic.getMaxMovementTime() == null || statistic.getMaxMovementTime() < trip.getMaxMovementTime()) {
                statistic.setMaxMovementTime(trip.getMaxMovementTime());
            }
            if (statistic.getMinIdleTime() == null || statistic.getMinIdleTime() > trip.getMinIdleTime()) {
                statistic.setMinIdleTime(trip.getMinIdleTime());
                ;
            }
            if (statistic.getMinMovementTime() == null || statistic.getMinMovementTime() > trip.getMinMovementTime()) {
                statistic.setMinMovementTime(trip.getMinMovementTime());
            }
            for (TripOfHour tripOfHour : trip.getTripOfHours()) {
                StatisticOfHour statisticOfHour = hourMap.get(tripOfHour.getHour());
                if (statisticOfHour == null) {
                    statisticOfHour = new StatisticOfHour();
                    statisticOfHour.setHour(tripOfHour.getHour());
                }
                statisticOfHour.setDistance(statisticOfHour.getDistance() + tripOfHour.getDistance());
                statisticOfHour.setTime(statisticOfHour.getTime() + tripOfHour.getTime());
                hourMap.put(statisticOfHour.getHour(), statisticOfHour);
            }
        }
        statistic.setTripCount((long) trips.size());
        statistic.setMaxSpeed(trips.stream().mapToDouble(Trip::getMaxSpeed).summaryStatistics().getMax());
        statistic.setAverageSpeed(trips.stream().mapToDouble(Trip::getAverageSpeed).summaryStatistics().getAverage());
        statistic.setOverSpeedDuration(trips.stream().mapToDouble(Trip::getSpeedDistributionOverSpeed).summaryStatistics().getAverage());
        statistic.setHighOverSpeedingDuration(trips.stream().mapToDouble(Trip::getSpeedDistributionHighOverSpeed).summaryStatistics().getAverage());
        statistic.setUnderLimitedSpeedDuration(trips.stream().mapToDouble(Trip::getSpeedDistributionUnderLimit).summaryStatistics().getAverage());
        final DoubleSummaryStatistics scoreSummaryStatistics = trips.stream().mapToDouble(Trip::getScore).summaryStatistics();
        statistic.setSumScore(scoreSummaryStatistics.getSum());
        statistic.setAverageScore(scoreSummaryStatistics.getAverage());
        int dayDuration = 0;
        int nightDuration = 0;
        for (Integer hour : hourMap.keySet()) {
            if (hour < 4 || hour > 19) {
                dayDuration += hourMap.get(hour).getTime();
            } else {
                nightDuration += hourMap.get(hour).getTime();
            }
            statistic.getTripOfHours().add(hourMap.get(hour));
        }
        statistic.setDayDuration(dayDuration);
        statistic.setNightDuration(nightDuration);
        statistic.setDate(new Date());
        statistic.setUser(userDa.get(userId));
        statistic = statisticDa.save(statistic);
        JmsDailyAnalyzeRequest jmsDailyAnalyzeRequest = new JmsDailyAnalyzeRequest();
        jmsDailyAnalyzeRequest.setUserId(userId);
        jmsDailyAnalyzeRequest.setDailyTripInfo(statistic.getId());
        messageProducer.send(jmsDailyAnalyzeRequest);
    }

    public List<Long> getDrivingUserIds(Date fromDate, Date toDate) {
        return userDa.getDrivingUserIds(fromDate, toDate);
    }

}
