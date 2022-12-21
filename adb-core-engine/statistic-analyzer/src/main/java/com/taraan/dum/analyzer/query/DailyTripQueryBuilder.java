package com.taraan.dum.analyzer.query;

import com.taraan.dum.model.hibernate.DailyTripInfo;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DailyTripQueryBuilder extends QueryBuilder<DailyTripInfo> implements ApplicationContextAware {
    public DailyTripQueryBuilder() {
    }

    protected static ApplicationContext applicationContext;

    @Override

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DailyTripQueryBuilder.applicationContext = applicationContext;
        this.defineSessionFactory(DailyTripQueryBuilder.applicationContext.getBean(SessionFactory.class));
    }

    public static DailyTripQueryBuilder createInstance() {
        return new DailyTripQueryBuilder("Select dailyTripInfo from DailyTripInfo dailyTripInfo ");
    }

    private DailyTripQueryBuilder(String queryString) {
        super(queryString);
        this.defineSessionFactory(this.applicationContext.getBean(SessionFactory.class));
    }

    /**
     * sort result by id desc
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder setOrderIdDesc() {
        this.orderQueryString = "dailyTripInfo.id desc";
        return this;
    }

    /**
     * @param id filter DailyTrip.id == id
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder idEqual(Long id) {
        this.whereString += " and dailyTripInfo.id = :ID_EQUAL";
        this.parameters.put("ID_EQUAL", id);
        return this;
    }

    /**
     * @param id filter DailyTrip.id != id
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder idNotEqual(Long id) {
        this.whereString += " and dailyTripInfo.id <> :ID_NOT_EQUAL";
        this.parameters.put("ID_NOT_EQUAL", id);
        return this;
    }

    /**
     * @param id filter DailyTrip.id &lt; id
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder idBefore(Long id) {
        this.whereString += " and dailyTripInfo.id < :ID_BEFORE";
        this.parameters.put("ID_BEFORE", id);
        return this;
    }

    /**
     * @param id filter DailyTrip.id &gt; id
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder idAfter(Long id) {
        this.whereString += " and dailyTripInfo.id > :ID_AFTER";
        this.parameters.put("ID_AFTER", id);
        return this;
    }

    /**
     * @param date filter DailyTrip.date &lt; date
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder dailyTripDateBefore(Date date) {
        this.whereString += " and dailyTripInfo.date < :DAILY_TRIP_DATE_BEFORE";
        this.parameters.put("DAILY_TRIP_DATE_BEFORE", date);
        return this;
    }

    /**
     * @param date filter DailyTrip.date &gt; date
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder dailyTripDateAfter(Date date) {
        this.whereString += " and dailyTripInfo.date > :DAILY_TRIP_DATE_AFTER";
        this.parameters.put("DAILY_TRIP_DATE_AFTER", date);
        return this;
    }

    /**
     * @param dayDuration filter DailyTrip.dayDuration &lt; dayDuration
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder dayDurationBefore(int dayDuration) {
        this.whereString += " and dailyTripInfo.dayDuration < :DAY_DURATION_BEFORE";
        this.parameters.put("DAY_DURATION_BEFORE", dayDuration);
        return this;
    }

    /**
     * @param dayDuration filter DailyTrip.dayDuration &gt; dayDuration
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder dayDurationAfter(int dayDuration) {
        this.whereString += " and dailyTripInfo.dayDuration > :DAY_DURATION_AFTER";
        this.parameters.put("DAY_DURATION_AFTER", dayDuration);
        return this;
    }

    /**
     * @param nightDuration filter DailyTrip.nightDuration &lt; nightDuration
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder nightDurationBefore(int nightDuration) {
        this.whereString += " and dailyTripInfo.nightDuration < :NIGHT_DURATION_BEFORE";
        this.parameters.put("NIGHT_DURATION_BEFORE", nightDuration);
        return this;
    }

    /**
     * @param nightDuration filter DailyTrip.nightDuration &gt; nightDuration
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder nightDurationAfter(int nightDuration) {
        this.whereString += " and dailyTripInfo.nightDuration > :NIGHT_DURATION_AFTER";
        this.parameters.put("NIGHT_DURATION_AFTER", nightDuration);
        return this;
    }


    /**
     * @param averageSpeed filter DailyTrip.averageSpeed &lt; averageSpeed
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder averageSpeedBefore(double averageSpeed) {
        this.whereString += " and dailyTripInfo.averageSpeed < :AVERAGE_SPEED_BEFORE";
        this.parameters.put("AVERAGE_SPEED_BEFORE", averageSpeed);
        return this;
    }

    /**
     * @param averageSpeed filter DailyTrip.averageSpeed &gt; averageSpeed
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder averageSpeedAfter(double averageSpeed) {
        this.whereString += " and dailyTripInfo.averageSpeed > :AVERAGE_SPEED_AFTER";
        this.parameters.put("AVERAGE_SPEED_AFTER", averageSpeed);
        return this;
    }

    /**
     * @param overSpeedDuration filter DailyTrip.overSpeedDuration &lt; overSpeedDuration
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder overSpeedDurationBefore(double overSpeedDuration) {
        this.whereString += " and dailyTripInfo.overSpeedDuration < :OVER_SPEED_DURATION_BEFORE";
        this.parameters.put("AVERAGE_SPEED_BEFORE", overSpeedDuration);
        return this;
    }

    /**
     * @param overSpeedDuration filter DailyTrip.overSpeedDuration &gt; overSpeedDuration
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder overSpeedDurationAfter(double overSpeedDuration) {
        this.whereString += " and dailyTripInfo.overSpeedDuration > :OVER_SPEED_DURATION_AFTER";
        this.parameters.put("AVERAGE_SPEED_AFTER", overSpeedDuration);
        return this;
    }


    /**
     * @param maxSpeed filter DailyTrip.maxSpeed &lt; maxSpeed
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder maxSpeedBefore(double maxSpeed) {
        this.whereString += " and dailyTripInfo.maxSpeed < :MAX_SPEED_BEFORE";
        this.parameters.put("MAX_SPEED_BEFORE", maxSpeed);
        return this;
    }

    /**
     * @param maxSpeed filter DailyTrip.maxSpeed &gt; maxSpeed
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder maxSpeedAfter(double maxSpeed) {
        this.whereString += " and dailyTripInfo.maxSpeed > :MAX_SPEED_AFTER";
        this.parameters.put("MAX_SPEED_AFTER", maxSpeed);
        return this;
    }

    /**
     * @param corneringCount filter DailyTrip.corneringCount &lt; corneringCount
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder corneringCountBefore(double corneringCount) {
        this.whereString += " and dailyTripInfo.corneringCount < :CORNERING_COUNT_BEFORE";
        this.parameters.put("CORNERING_COUNT_BEFORE", corneringCount);
        return this;
    }

    /**
     * @param corneringCount filter DailyTrip.corneringCount &gt; corneringCount
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder corneringCountAfter(double corneringCount) {
        this.whereString += " and dailyTripInfo.corneringCount > :CORNERING_COUNT_AFTER";
        this.parameters.put("CORNERING_COUNT_AFTER", corneringCount);
        return this;
    }

    /**
     * @param brakingCount filter DailyTrip.brakingCount &lt; brakingCount
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder brakingCountBefore(double brakingCount) {
        this.whereString += " and dailyTripInfo.brakingCount < :BRAKING_COUNT_BEFORE";
        this.parameters.put("BRAKING_COUNT_BEFORE", brakingCount);
        return this;
    }

    /**
     * @param brakingCount filter DailyTrip.brakingCount &gt; brakingCount
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder brakingCountAfter(double brakingCount) {
        this.whereString += " and dailyTripInfo.brakingCount > :BRAKING_COUNT_AFTER";
        this.parameters.put("BRAKING_COUNT_AFTER", brakingCount);
        return this;
    }

    /**
     * @param accelerationCount filter DailyTrip.accelerationCount &lt; accelerationCount
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder accelerationCountBefore(double accelerationCount) {
        this.whereString += " and dailyTripInfo.accelerationCount < :ACCELERATION_COUNT_BEFORE";
        this.parameters.put("ACCELERATION_COUNT_BEFORE", accelerationCount);
        return this;
    }

    /**
     * @param accelerationCount filter DailyTrip.accelerationCount &gt; accelerationCount
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder accelerationCountAfter(double accelerationCount) {
        this.whereString += " and dailyTripInfo.accelerationCount > :ACCELERATION_COUNT_AFTER";
        this.parameters.put("ACCELERATION_COUNT_AFTER", accelerationCount);
        return this;
    }

    /**
     * @param distance filter DailyTrip.distance &lt; distance
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder distanceBefore(double distance) {
        this.whereString += " and dailyTripInfo.distance < :DISTANCE_BEFORE";
        this.parameters.put("DISTANCE_BEFORE", distance);
        return this;
    }

    /**
     * @param distance filter DailyTrip.distance &gt; distance
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder distanceAfter(double distance) {
        this.whereString += " and dailyTripInfo.distance > :DISTANCE_AFTER";
        this.parameters.put("DISTANCE_AFTER", distance);
        return this;
    }

    /**
     * @param idleTime filter DailyTrip.idleTime &gt; idleTime
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder idleTimeAfter(double idleTime) {
        this.whereString += " and dailyTripInfo.idleTime > :IDLE_TIME_AFTER";
        this.parameters.put("IDLE_TIME_AFTER", idleTime);
        return this;
    }

    /**
     * @param idleTime filter DailyTrip.idleTime &lt; idleTime
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder idleTimeBefore(double idleTime) {
        this.whereString += " and dailyTripInfo.idleTime < :HIGHWAY_DISTANCE_BEFORE";
        this.parameters.put("OTHER_DISTANCE_BEFORE", idleTime);
        return this;
    }

    /**
     * @param duration filter DailyTrip.duration &gt; duration
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder durationAfter(double duration) {
        this.whereString += " and dailyTripInfo.duration > :DURATION_AFTER";
        this.parameters.put("DURATION_AFTER", duration);
        return this;
    }

    /**
     * @param duration filter DailyTrip.duration &lt; duration
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder durationBefore(double duration) {
        this.whereString += " and dailyTripInfo.duration < :DURATION_BEFORE";
        this.parameters.put("DURATION_BEFORE", duration);
        return this;
    }

    /**
     * @param minIdleTime filter DailyTrip.minIdleTime &gt; minIdleTime
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder minIdleTimeAfter(double minIdleTime) {
        this.whereString += " and dailyTripInfo.minIdleTime > :MIN_IDLE_TIME_AFTER";
        this.parameters.put("MIN_IDLE_TIME_AFTER", minIdleTime);
        return this;
    }
    /**
     * @param minIdleTime filter DailyTrip.minIdleTime &lt; minIdleTime
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder minIdleTimeBefore(double minIdleTime) {
        this.whereString += " and dailyTripInfo.minIdleTime < :MIN_IDLE_TIME_BEFORE";
        this.parameters.put("MIN_IDLE_TIME_BEFORE", minIdleTime);
        return this;
    }
    /**
     * @param maxIdleTime filter DailyTrip.maxIdleTime &gt; maxIdleTime
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder maxIdleTimeAfter(double maxIdleTime) {
        this.whereString += " and dailyTripInfo.maxIdleTime > :MAX_IDLE_TIME_AFTER";
        this.parameters.put("MAX_IDLE_TIME_AFTER", maxIdleTime);
        return this;
    }

    /**
     * @param maxIdleTime filter DailyTrip.maxIdleTime &lt; maxIdleTime
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder maxIdleTimeBefore(double maxIdleTime) {
        this.whereString += " and dailyTripInfo.maxIdleTime < :MAX_IDLE_TIME_BEFORE";
        this.parameters.put("MAX_IDLE_TIME_BEFORE", maxIdleTime);
        return this;
    }


    /**
     * @param userId filter DailyTrip.user.id = userId
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder tripUserId(Long userId) {
        this.whereString += " and dailyTripInfo.user.id = :DAILY_USER_ID";
        this.parameters.put("DAILY_USER_ID", userId);
        return this;
    }

    /**
     * @param userIds filter DailyTrip.user.id contains userIds
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder tripUserIds(List<Long> userIds) {
        this.whereString += " and dailyTripInfo.user.id  in  (:DAILY_USER_IDS)";
        this.parameters.put("DAILY_USER_IDS", userIds);
        return this;
    }

    /**
     * @param first set first index of result = first
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder setFirstResult(int first) {
        this.firstResult = first;
        return this;
    }

    /**
     * @param maxResult set result size = maxResult
     *
     * @return DailyTripQueryBuilder
     */
    public DailyTripQueryBuilder setMaxResult(int maxResult) {
        this.maxResult = maxResult;
        return this;
    }


}
