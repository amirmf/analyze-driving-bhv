package com.taraan.dum.analyzer.query;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.taraan.dum.model.hibernate.Trip;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TripQueryBuilder extends QueryBuilder<Trip> implements ApplicationContextAware {

    public TripQueryBuilder() {
    }

    public static TripQueryBuilder createInstance() {
        return new TripQueryBuilder("Select trip from Trip trip ");
    }

    protected static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        TripQueryBuilder.applicationContext = applicationContext;
        this.defineSessionFactory(TripQueryBuilder.applicationContext.getBean(SessionFactory.class));
    }

    private TripQueryBuilder(String queryString) {
        super(queryString);
        this.defineSessionFactory(this.applicationContext.getBean(SessionFactory.class));
    }

    /**
     * sort result by id desc
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder setOrderIdDesc() {
        this.orderQueryString = "trip.id desc";
        return this;
    }

    /**
     * @param id filter trip.id = id
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder idEqual(Long id) {
        this.whereString += " and trip.id = :ID_EQUAL";
        this.parameters.put("ID_EQUAL", id);
        return this;
    }

    /**
     * @param id filter trip.id != id
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder idNotEqual(Long id) {
        this.whereString += " and trip.id <> :ID_NOT_EQUAL";
        this.parameters.put("ID_NOT_EQUAL", id);
        return this;
    }

    /**
     * @param id filter trip.id &lt; id
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder idBefore(Long id) {
        this.whereString += " and trip.id < :ID_BEFORE";
        this.parameters.put("ID_BEFORE", id);
        return this;
    }

    /**
     * @param id filter trip.id &gt; id
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder idAfter(Long id) {
        this.whereString += " and trip.id > :ID_AFTER";
        this.parameters.put("ID_AFTER", id);
        return this;
    }

    /**
     * @param isValid filter trip.valid == isValid
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder setValid(boolean isValid) {
        this.whereString += " and trip.valid = :VALID";
        this.parameters.put("VALID", isValid);
        return this;
    }

    /**
     * @param isRemoved filter trip.removed == isRemoved
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder setRemoved(boolean isRemoved) {
        this.whereString += " and trip.removed = :REMOVED";
        this.parameters.put("REMOVED", isRemoved);
        return this;
    }


    /**
     * @param date filter trip.tripStartDate &lt; date
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder tripStartDateBefore(Date date) {
        this.whereString += " and trip.tripStartDate < :TRIP_START_DATE_BEFORE";
        this.parameters.put("TRIP_START_DATE_BEFORE", date);
        return this;
    }

    /**
     * @param date filter trip.tripStartDate &gt; date
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder tripStartDateAfter(Date date) {
        this.whereString += " and trip.tripStartDate > :TRIP_START_DATE_AFTER";
        this.parameters.put("TRIP_START_DATE_AFTER", date);
        return this;
    }


    /**
     * @param date filter trip.tripEndDate &lt; date
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder tripEndDateBefore(Date date) {
        this.whereString += " and trip.tripEndDate < :TRIP_END_DATE_BEFORE";
        this.parameters.put("TRIP_END_DATE_BEFORE", date);
        return this;
    }

    /**
     * @param date trip.tripEndDate &gt; date
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder tripEndDateAfter(Date date) {
        this.whereString += " and trip.tripEndDate > :TRIP_END_DATE_AFTER";
        this.parameters.put("TRIP_END_DATE_AFTER", date);
        return this;
    }

    /**
     * @param userId trip.user.id = userId
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder tripUserId(Long userId) {
        this.whereString += " and trip.user.id = :TRIP_USER_ID";
        this.parameters.put("TRIP_USER_ID", userId);
        return this;
    }

    /**
     * @param userIds trip.user.id contains userIds
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder tripUserIds(List<Long> userIds) {
        this.whereString += " and trip.user.id  in  (:TRIP_USER_IDS)";
        this.parameters.put("TRIP_USER_IDS", userIds);
        return this;
    }

    /**
     * @param speedDistributionUnderLimit filter trip.speedDistributionUnderLimit &lt; speedDistributionUnderLimit
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder speedDistributionUnderLimitBefore(double speedDistributionUnderLimit) {
        this.whereString += " and trip.speedDistributionUnderLimit < :SPEED_DISTRIBUTION_UNDER_LIMIT_BEFORE";
        this.parameters.put("SPEED_DISTRIBUTION_UNDER_LIMIT_BEFORE", speedDistributionUnderLimit);
        return this;
    }

    /**
     * @param speedDistributionUnderLimit filter trip.speedDistributionUnderLimit &gt; speedDistributionUnderLimit
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder speedDistributionUnderLimitAfter(double speedDistributionUnderLimit) {
        this.whereString += " and trip.speedDistributionUnderLimit > :SPEED_DISTRIBUTION_UNDER_LIMIT_AFTER";
        this.parameters.put("SPEED_DISTRIBUTION_UNDER_LIMIT_AFTER", speedDistributionUnderLimit);
        return this;
    }

    /**
     * @param score filter trip.score &lt; score
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder scoreBefore(Long score) {
        this.whereString += " and trip.score < :SCORE_BEFORE";
        this.parameters.put("SCORE_BEFORE", score);
        return this;
    }

    /**
     * @param score filter trip.score &gt; score
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder scoreAfter(Long score) {
        this.whereString += " and trip.score > :SCORE_AFTER";
        this.parameters.put("SCORE_AFTER", score);
        return this;
    }


    /**
     * @param speedDistributionOverSpeed filter trip.speedDistributionOverSpeed &lt; speedDistributionOverSpeed
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder speedDistributionOverSpeedBefore(double speedDistributionOverSpeed) {
        this.whereString += " and trip.speedDistributionOverSpeed < :SPEED_DISTRIBUTION_OVER_SPEED_BEFORE";
        this.parameters.put("SPEED_DISTRIBUTION_OVER_SPEED_BEFORE", speedDistributionOverSpeed);
        return this;
    }

    /**
     * @param speedDistributionOverSpeed filter trip.speedDistributionOverSpeed &gt; speedDistributionOverSpeed
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder speedDistributionOverSpeedAfter(double speedDistributionOverSpeed) {
        this.whereString += " and trip.speedDistributionOverSpeed > :SPEED_DISTRIBUTION_OVER_LIMIT_AFTER";
        this.parameters.put("SPEED_DISTRIBUTION_OVER_LIMIT_AFTER", speedDistributionOverSpeed);
        return this;
    }

    /**
     * @param speedDistributionOverSpeed filter trip.speedDistributionOverSpeed &lt; speedDistributionOverSpeed
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder speedDistributionHighOverSpeedBefore(double speedDistributionOverSpeed) {
        this.whereString += " and trip.speedDistributionHighOverSpeed < :SPEED_DISTRIBUTION_HIGH_OVER_SPEED_BEFORE";
        this.parameters.put("SPEED_DISTRIBUTION_HIGH_OVER_SPEED_BEFORE", speedDistributionOverSpeed);
        return this;
    }

    /**
     * @param speedDistributionOverSpeed filter trip.speedDistributionOverSpeed &gt; speedDistributionOverSpeed
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder speedDistributionHighOverSpeedAfter(double speedDistributionOverSpeed) {
        final String speed_distribution_high_over_speed_after = "SPEED_DISTRIBUTION_HIGH_OVER_SPEED_AFTER";
        this.whereString += " and trip.speedDistributionHighOverSpeed > :" + speed_distribution_high_over_speed_after;
        this.parameters.put(speed_distribution_high_over_speed_after, speedDistributionOverSpeed);
        return this;
    }

    /**
     * @param averageSpeed filter trip.averageSpeed &lt; averageSpeed
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder averageSpeedBefore(double averageSpeed) {
        this.whereString += " and trip.averageSpeed < :AVERAGE_SPEED_BEFORE";
        this.parameters.put("AVERAGE_SPEED_BEFORE", averageSpeed);
        return this;
    }

    /**
     * @param averageSpeed filter trip.averageSpeed &gt; averageSpeed
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder averageSpeedAfter(double averageSpeed) {
        this.whereString += " and trip.averageSpeed > :AVERAGE_SPEED_AFTER";
        this.parameters.put("AVERAGE_SPEED_AFTER", averageSpeed);
        return this;
    }

    /**
     * @param maxSpeed filter trip.maxSpeed &lt; maxSpeed
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder maxSpeedBefore(double maxSpeed) {
        this.whereString += " and trip.maxSpeed < :MAX_SPEED_BEFORE";
        this.parameters.put("MAX_SPEED_BEFORE", maxSpeed);
        return this;
    }

    /**
     * @param maxSpeed filter trip.maxSpeed &gt; maxSpeed
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder maxSpeedAfter(double maxSpeed) {
        this.whereString += " and trip.maxSpeed > :MAX_SPEED_AFTER";
        this.parameters.put("MAX_SPEED_AFTER", maxSpeed);
        return this;
    }

    /**
     * @param corneringCount filter trip.corneringCount &lt; corneringCount
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder corneringCountBefore(double corneringCount) {
        this.whereString += " and trip.corneringCount < :CORNERING_COUNT_BEFORE";
        this.parameters.put("CORNERING_COUNT_BEFORE", corneringCount);
        return this;
    }

    /**
     * @param corneringCount filter trip.corneringCount &gt; corneringCount
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder corneringCountAfter(double corneringCount) {
        this.whereString += " and trip.corneringCount > :CORNERING_COUNT_AFTER";
        this.parameters.put("CORNERING_COUNT_AFTER", corneringCount);
        return this;
    }

    /**
     * @param brakingCount filter trip.brakingCount &lt; brakingCount
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder brakingCountBefore(double brakingCount) {
        this.whereString += " and trip.brakingCount < :BRAKING_COUNT_BEFORE";
        this.parameters.put("BRAKING_COUNT_BEFORE", brakingCount);
        return this;
    }

    /**
     * @param brakingCount filter trip.brakingCount &gt; brakingCount
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder brakingCountAfter(double brakingCount) {
        this.whereString += " and trip.brakingCount > :BRAKING_COUNT_AFTER";
        this.parameters.put("BRAKING_COUNT_AFTER", brakingCount);
        return this;
    }

    /**
     * @param accelerationCount filter trip.accelerationCount &lt; accelerationCount
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder accelerationCountBefore(double accelerationCount) {
        this.whereString += " and trip.accelerationCount < :ACCELERATION_COUNT_BEFORE";
        this.parameters.put("ACCELERATION_COUNT_BEFORE", accelerationCount);
        return this;
    }

    /**
     * @param accelerationCount filter trip.accelerationCount &gt; accelerationCount
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder accelerationCountAfter(double accelerationCount) {
        this.whereString += " and trip.accelerationCount > :ACCELERATION_COUNT_AFTER";
        this.parameters.put("ACCELERATION_COUNT_AFTER", accelerationCount);
        return this;
    }

    /**
     * @param distance filter trip.distance &lt; distance
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder distanceBefore(double distance) {
        this.whereString += " and trip.distance < :DISTANCE_BEFORE";
        this.parameters.put("DISTANCE_BEFORE", distance);
        return this;
    }

    /**
     * @param distance filter trip.distance &gt; distance
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder distanceAfter(double distance) {
        this.whereString += " and trip.distance > :DISTANCE_AFTER";
        this.parameters.put("DISTANCE_AFTER", distance);
        return this;
    }

    /**
     * @param otherDistance filter trip.otherDistance &lt; otherDistance
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder otherDistanceBefore(double otherDistance) {
        this.whereString += " and trip.otherDistance < :OTHER_DISTANCE_BEFORE";
        this.parameters.put("OTHER_DISTANCE_BEFORE", otherDistance);
        return this;
    }

    /**
     * @param otherDistance filter trip.otherDistance &gt; otherDistance
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder otherDistanceAfter(double otherDistance) {
        this.whereString += " and trip.otherDistance > :OTHER_DISTANCE_AFTER";
        this.parameters.put("OTHER_DISTANCE_AFTER", otherDistance);
        return this;
    }

    /**
     * @param urbanDistance filter trip.urbanDistance &lt; urbanDistance
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder urbanDistanceBefore(double urbanDistance) {
        this.whereString += " and trip.urbanDistance < :URBAN_DISTANCE_BEFORE";
        this.parameters.put("OTHER_DISTANCE_BEFORE", urbanDistance);
        return this;
    }

    /**
     * @param urbanDistance filter trip.urbanDistance &gt; urbanDistance
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder urbanDistanceAfter(double urbanDistance) {
        this.whereString += " and trip.urbanDistance > :URBAN_DISTANCE_AFTER";
        this.parameters.put("URBAN_DISTANCE_AFTER", urbanDistance);
        return this;
    }

    /**
     * @param highwayDistance filter trip.highwayDistance &lt; highwayDistance
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder highwayDistanceBefore(double highwayDistance) {
        this.whereString += " and trip.highwayDistance < :HIGHWAY_DISTANCE_BEFORE";
        this.parameters.put("HIGHWAY_DISTANCE_BEFORE", highwayDistance);
        return this;
    }


    /**
     * @param highwayDistance filter trip.highwayDistance &gt; highwayDistance
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder highwayDistanceAfter(double highwayDistance) {
        this.whereString += " and trip.highwayDistance > :HIGHWAY_DISTANCE_AFTER";
        this.parameters.put("HIGHWAY_DISTANCE_AFTER", highwayDistance);
        return this;
    }
    /**
     * @param idleTime filter trip.idleTime &gt; idleTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder idleTimeAfter(double idleTime) {
        this.whereString += " and trip.idleTime > :IDLE_TIME_AFTER";
        this.parameters.put("IDLE_TIME_AFTER", idleTime);
        return this;
    }

    /**
     * @param idleTime filter trip.idleTime &lt; idleTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder idleTimeBefore(double idleTime) {
        this.whereString += " and trip.idleTime < :IDLE_TIME_BEFORE";
        this.parameters.put("IDLE_TIME_BEFORE", idleTime);
        return this;
    }

    /**
     * @param duration filter trip.duration &gt; duration
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder durationAfter(double duration) {
        this.whereString += " and trip.duration > :DURATION_AFTER";
        this.parameters.put("DURATION_AFTER", duration);
        return this;
    }

    /**
     * @param duration filter trip.duration &lt; duration
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder durationBefore(double duration) {
        this.whereString += " and trip.duration < :DURATION_BEFORE";
        this.parameters.put("DURATION_BEFORE", duration);
        return this;
    }

    /**
     * @param badDirection filter trip.badDirection &gt; badDirection
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder badDirectionAfter(double badDirection) {
        this.whereString += " and trip.badDirection > :BAD_DIRECTION_AFTER";
        this.parameters.put("BAD_DIRECTION_AFTER", badDirection);
        return this;
    }

    /**
     * @param badDirection filter trip.badDirection &lt; badDirection
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder badDirectionBefore(double badDirection) {
        this.whereString += " and trip.badDirection < :BAD_DIRECTION_BEFORE";
        this.parameters.put("BAD_DIRECTION_BEFORE", badDirection);
        return this;
    }

    /**
     * @param minIdleTime filter trip.minIdleTime &gt; minIdleTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder minIdleTimeAfter(double minIdleTime) {
        this.whereString += " and trip.minIdleTime > :MIN_IDLE_TIME_AFTER";
        this.parameters.put("MIN_IDLE_TIME_AFTER", minIdleTime);
        return this;
    }

    /**
     * @param minIdleTime filter trip.minIdleTime &lt; minIdleTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder minIdleTimeBefore(double minIdleTime) {
        this.whereString += " and trip.minIdleTime < :MIN_IDLE_TIME_BEFORE";
        this.parameters.put("MIN_IDLE_TIME_BEFORE", minIdleTime);
        return this;
    }

    /**
     * @param maxIdleTime filter trip.maxIdleTime &gt; maxIdleTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder maxIdleTimeAfter(double maxIdleTime) {
        this.whereString += " and trip.maxIdleTime > :MAX_IDLE_TIME_AFTER";
        this.parameters.put("MAX_IDLE_TIME_AFTER", maxIdleTime);
        return this;
    }

    /**
     * @param maxIdleTime filter trip.maxIdleTime &lt; maxIdleTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder maxIdleTimeBefore(double maxIdleTime) {
        this.whereString += " and trip.maxIdleTime < :MAX_IDLE_TIME_BEFORE";
        this.parameters.put("MAX_IDLE_TIME_BEFORE", maxIdleTime);
        return this;
    }

    /**
     * @param maxMovementTime filter trip.maxMovementTime &gt; maxMovementTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder maxMovementTimeAfter(double maxMovementTime) {
        this.whereString += " and trip.maxMovementTime > :MAX_MOVEMENT_TIME_AFTER";
        this.parameters.put("MAX_MOVEMENT_TIME_AFTER", maxMovementTime);
        return this;
    }

    /**
     * @param maxMovementTime filter trip.maxMovementTime &lt; maxMovementTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder maxMovementTimeBefore(double maxMovementTime) {
        this.whereString += " and trip.maxMovementTime < :MAX_MOVEMENT_TIME_BEFORE";
        this.parameters.put("MAX_MOVEMENT_TIME_BEFORE", maxMovementTime);
        return this;
    }

    /**
     * @param minMovementTime filter trip.minMovementTime &gt; minMovementTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder minMovementTimeAfter(double minMovementTime) {
        this.whereString += " and trip.minMovementTime > :MIN_MOVEMENT_TIME_AFTER";
        this.parameters.put("MIN_MOVEMENT_TIME_AFTER", minMovementTime);
        return this;
    }

    /**
     * @param minMovementTime filter trip.minMovementTime &lt; minMovementTime
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder minMovementTimeBefore(double minMovementTime) {
        this.whereString += " and trip.minMovementTime < :MIN_MOVEMENT_TIME_BEFORE";
        this.parameters.put("MIN_MOVEMENT_TIME_BEFORE", minMovementTime);
        return this;
    }

    /**
     * @param first set first index of result = first
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder setFirstResult(int first) {
        this.firstResult = first;
        return this;
    }

    /**
     * @param maxResult set result size = maxResult
     *
     * @return TripQueryBuilder
     */
    public TripQueryBuilder setMaxResult(int maxResult) {
        this.maxResult = maxResult;
        return this;
    }


}
