package com.taraan.dum.logic;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.da.*;
import com.taraan.dum.dto.news.NewsPage;
import com.taraan.dum.dto.statistic.*;
import com.taraan.dum.mappers.RewardMapper;
import com.taraan.dum.model.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class StatisticLogic {

    @Autowired
    private TripDa tripDa;
    @Autowired
    private MessageDa messageDa;
    @Autowired
    private NewsLogic newsLogic;
    @Autowired
    private ChallengeLogic challengeLogic;
    @Autowired
    private UserScoreDa userScoreDa;
    @Autowired
    private UserBadgeDa userBadgeDa;
    @Autowired
    private BadgeDa badgeDa;
    @Autowired
    private DailyTripInfoDa dailyTripInfoDa;
    @Value("${iconPath}")
    private String iconPath;
    @Value("${newsPath}")
    private String newsPath;


    private Long randomLong(long leftLimit, long rightLimit) {
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    public HomeDataDto getHomeDate(Long userId) {

        HomeDataDto homeDataDto = new HomeDataDto();
        final Double averageScore = userScoreDa.getAverageScore(userId);
        homeDataDto.setScore(averageScore.longValue());
        homeDataDto.setTripCount(tripDa.getCount(userId));
        homeDataDto.setAverageSpeed(tripDa.getAverageSpeed(userId).longValue());
        homeDataDto.setSumDistance(tripDa.getSumDistance(userId));
        homeDataDto.setSumTimeDuration(tripDa.getSumDuration(userId) / 3600);
        homeDataDto.setUnreadMessageCount(messageDa.getUnreadCountByUser(userId));
        Double sumDurationHighway = tripDa.getSumHighwayDistance(userId);
        Double sumUrbanDistance = tripDa.getSumUrbanDistance(userId);
        Double sumOtherDistance = tripDa.getSumOtherDistance(userId);
        final Long sumWeekendTime = tripDa.getSumTime(userId, true);
        final Long sumWeekTime = tripDa.getSumTime(userId, false);
        final long sumWeek = sumWeekendTime + sumWeekTime;
        if (sumWeek != 0) {
            homeDataDto.setWeekendPercentage(Math.ceil((sumWeekendTime * 100) / sumWeek));
            homeDataDto.setWeekPercentage(100 - homeDataDto.getWeekendPercentage());
        } else {
            homeDataDto.setWeekendPercentage(0);
            homeDataDto.setWeekPercentage(0);

        }
        final double sumDistance = sumDurationHighway + sumOtherDistance + sumUrbanDistance;
        if ((sumDistance) != 0) {
            homeDataDto.setHighwayPercentage(Math.ceil((sumDurationHighway * 100) / sumDistance));
            homeDataDto.setUrbanPercentage(Math.ceil((sumUrbanDistance * 100) / sumDistance));
            homeDataDto.setOtherPercentage(100 - (homeDataDto.getHighwayPercentage() + homeDataDto.getUrbanPercentage()));
        } else {
            homeDataDto.setHighwayPercentage(0);
            homeDataDto.setUrbanPercentage(0);
            homeDataDto.setOtherPercentage(0);
        }
        homeDataDto.setChallengeItems(challengeLogic.getDisplayChallenge(0L, 2L));
        homeDataDto.setRewards(this.getUserBadges(userId, 0, 3));
        final NewsPage newses = newsLogic.getNewses(0L, 1L);
        if (!newses.getNewses().isEmpty())
            homeDataDto.setNews(newses.getNewses().get(0));
        return homeDataDto;
    }

    private List<RewardDto> getUserBadges(Long userId, Integer from, Integer size) {
        List<RewardDto> rewards = new ArrayList<>();
        final List<BadgeUserDto> rewardByUser = userBadgeDa.getRewardByUser(userId, from, size);
        final List<Long> badgeIds = rewardByUser.stream().map(BadgeUserDto::getBadge).collect(Collectors.toList());
        for (BadgeUserDto userBadge : rewardByUser) {
            rewards.add(RewardMapper.getDto(badgeDa.get(userBadge.getBadge()), userBadge.getCount(), iconPath));
        }
        if (badgeIds.size() < size) {
            List<Badge> badges;
            if (badgeIds.isEmpty())
                badges = badgeDa.get(false, null, null, Long.valueOf(from), (long) (size - badgeIds.size()));
            else
                badges = badgeDa.getByNotIds(badgeIds, from, size - badgeIds.size());
            for (Badge badge : badges) {
                rewards.add(RewardMapper.getDto(badge, 0L, iconPath));
            }
        }
        return rewards;
    }

    public List<RewardDto> getRewards(Long userId) {
        return getUserBadges(userId, 0, 1000);
    }

    public StatisticDto getStatistics(Long userId, String from, String to) {
        Date fromDate = DateUtils.getDate(from);
        Date toDate = DateUtils.getDate(to);
        if (toDate.getTime() < fromDate.getTime()) {
            Date temp = toDate;
            toDate = fromDate;
            fromDate = temp;

        }
        Date statisticFromDate = DateUtils.addDay(fromDate, 1);
        Date statisticToDate = DateUtils.addDay(toDate, 1);

        Date now = new Date();
        List<DailyTripInfo> dailyTripInfos = dailyTripInfoDa.get(userId, DateUtils.getStartOfDay(statisticFromDate), DateUtils.getEndOfDay(statisticToDate));
        if (DateUtils.isBetween(now, DateUtils.getStartOfDay(fromDate), DateUtils.getEndOfDay(toDate)))
            dailyTripInfos.add(getToday(userId, DateUtils.getStartOfDay(now), DateUtils.getEndOfDay(now)));

        return getAggregateStatistic(dailyTripInfos);
    }

    private DailyTripInfo getToday(Long userId, Date from, Date to) {
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
        statistic.setSumScore(trips.stream().mapToDouble(Trip::getScore).summaryStatistics().getSum());
        statistic.setAverageScore(trips.stream().mapToDouble(Trip::getScore).summaryStatistics().getAverage());
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
        return statistic;
    }

    private StatisticDto getAggregateStatistic(List<DailyTripInfo> dailyTripInfos) {
        StatisticDto statistic = new StatisticDto();
        if (dailyTripInfos.isEmpty())
            return statistic;
        double accelerationCount = 0d;
        double brakingCount = 0d;
        double corneringCount = 0d;
        double maxSpeed = 0d;
        double averageSpeed = 0d;
        double overSpeedingTime = 0d;
        double overSpeedCount = 0d;
        double urbanDuration = 0d;
        double highwayDuration = 0d;
        double otherRoadDuration = 0d;
        double duration = 0;//todo time duration
        double distance = 0;//
        double idleTime = 0;//
        Map<Integer, StatisticOfHour> hourMap = new HashMap<>();
        double averageScore = 0;

        for (DailyTripInfo dailyTripInfo : dailyTripInfos) {
            distance += dailyTripInfo.getDistance();
            idleTime += dailyTripInfo.getIdleTime();
            duration += dailyTripInfo.getDuration();
            statistic.setTripCount(statistic.getTripCount() + dailyTripInfo.getTripCount());
            corneringCount += dailyTripInfo.getCorneringCount();
            brakingCount += dailyTripInfo.getBrakingCount();
            accelerationCount += dailyTripInfo.getAccelerationCount();
            overSpeedingTime += dailyTripInfo.getOverSpeedDuration();
            urbanDuration += dailyTripInfo.getUrbanDuration();
            highwayDuration += dailyTripInfo.getHighWayDuration();
            otherRoadDuration += dailyTripInfo.getOtherRoadDuration();
            averageSpeed += dailyTripInfo.getAverageSpeed();
            if (maxSpeed < dailyTripInfo.getMaxSpeed())
                maxSpeed = dailyTripInfo.getMaxSpeed();
            averageScore += dailyTripInfo.getAverageScore();
            for (StatisticOfHour tripOfHour : dailyTripInfo.getTripOfHours()) {
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
        final Double score = (averageScore / dailyTripInfos.size()) % 100;
        statistic.setScore(score.longValue());
        statistic.setDistance(distance);
        statistic.setAverageDistance(distance / dailyTripInfos.size());
        statistic.setDuration(duration);
        statistic.setAverageSpeed(averageSpeed / dailyTripInfos.size());
        if (duration != 0) {
            statistic.setHighwayPercentage(Math.ceil((highwayDuration * 100) / duration));
            statistic.setUrbanPercentage(Math.ceil((urbanDuration * 100) / duration));
            statistic.setOtherPercentage(100 - (statistic.getUrbanPercentage() + statistic.getHighwayPercentage()));
        } else {
            statistic.setHighwayPercentage(0);
            statistic.setUrbanPercentage(0);
            statistic.setOtherPercentage(0);
        }
        statistic.setMaxSpeed(maxSpeed);
        statistic.setIdleTime(idleTime);
        statistic.setOverSpeedCount(overSpeedCount);
        statistic.setOverSpeedingTime(overSpeedingTime);
        double dailyDuration = 0d;
        double nightDuration = 0;
        List<StatisticOfHourDto> statisticOfHourDtos = new ArrayList<>();
        for (StatisticOfHour statisticOfHour : hourMap.values()) {
            StatisticOfHourDto statisticOfHourDto = new StatisticOfHourDto();
            statisticOfHourDto.setDistance(statisticOfHour.getDistance());
            statisticOfHourDto.setHour(statisticOfHour.getHour());
            statisticOfHourDto.setTime(statisticOfHour.getTime());
            statisticOfHourDtos.add(statisticOfHourDto);
            dailyDuration += statisticOfHour.getTime();
            if (statisticOfHour.getHour() < 4 || statisticOfHour.getHour() > 19) {
                nightDuration += statisticOfHour.getTime();
            }

        }
        statisticOfHourDtos.sort(Comparator.comparing(StatisticOfHourDto::getHour));
        statistic.setStatisticOfHourDtos(statisticOfHourDtos.toArray(new StatisticOfHourDto[]{}));
        if (dailyDuration != 0) {
            statistic.setNightPercentage(Math.ceil((nightDuration * 100) / dailyDuration));
            statistic.setDayPercentage(100 - statistic.getNightPercentage());
        }
        if (distance > 100) {
            statistic.setAccelerationEvent((accelerationCount * 100) / distance);
            statistic.setBrakingEvent((brakingCount * 100) / distance);
            statistic.setCorneringEvent((corneringCount * 100) / distance);
        } else {
            statistic.setAccelerationEvent(accelerationCount);
            statistic.setBrakingEvent(brakingCount);
            statistic.setCorneringEvent(corneringCount);
        }
        return statistic;
    }
}
