package com.taraan.dum.model.hibernate;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "DailyTripInfo")
@NamedQueries(
        @NamedQuery(name = "dailyTrip-by-rangeDate", query = "select info from DailyTripInfo info where info.date > :FROM_DATE and info.date < :TO_DATE and info.user.id =:USER")
)
public class DailyTripInfo implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistic_id_seq")
    @SequenceGenerator(sequenceName = "statistic_id_seq", allocationSize = 1, name = "statistic_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @Column(name = "tripCount")
    private Long tripCount;
    @Column(name = "accelerationCount")
    private int accelerationCount;
    @Column(name = "brakingCount")
    private int brakingCount;
    @Column(name = "corneringCount")
    private int corneringCount;
    @Column(name = "overSpeedCount")
    private double overSpeedCount;
    @Column(name = "maxSpeed")
    private double maxSpeed;
    @Column(name = "averageSpeed")
    private double averageSpeed;
    @Column(name = "overSpeedDuration")
    private double overSpeedDuration;
    @Column(name = "underLimitedSpeedDuration")
    private double underLimitedSpeedDuration;
    @Column(name = "highOverSpeedingDuration")
    private double highOverSpeedingDuration;
    @Column(name = "dayDuration")
    private int dayDuration;
    @Column(name = "nightDuration")
    private int nightDuration;
    @Column(name = "urbanDuration")
    private double urbanDuration;
    @Column(name = "highWayDuration")
    private double highWayDuration;
    @Column(name = "otherRoadDuration")
    private double otherRoadDuration;
    @Column(name = "Duration")
    private Long duration;
    @Column(name = "Distance")
    private double distance;
    @Column(name = "idleTime")
    private Long idleTime;
    @Column(name = "maxIdleTime")
    private Long maxIdleTime;
    @Column(name = "minIdleTime")
    private Long minIdleTime;
    @Column(name = "movementTime")
    private Long movementTime;
    @Column(name = "maxMovementTime")
    private Long maxMovementTime;
    @Column(name = "minMovementTime")
    private Long minMovementTime;
    @Column(name = "StatisticDate")
    private Date date;
    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "StatisticId")
    @OrderBy("Id")
    private List<StatisticOfHour> tripOfHours = new ArrayList<>();
    @Column(name = "sumScore")
    private double sumScore;
    @Column(name = "averageScore")
    private double averageScore;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAccelerationCount() {
        return accelerationCount;
    }

    public void setAccelerationCount(int accelerationCount) {
        this.accelerationCount = accelerationCount;
    }

    public int getBrakingCount() {
        return brakingCount;
    }

    public void setBrakingCount(int brakingCount) {
        this.brakingCount = brakingCount;
    }

    public int getCorneringCount() {
        return corneringCount;
    }

    public void setCorneringCount(int corneringCount) {
        this.corneringCount = corneringCount;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getOverSpeedDuration() {
        return overSpeedDuration;
    }

    public void setOverSpeedDuration(double overSpeedDuration) {
        this.overSpeedDuration = overSpeedDuration;
    }

    public int getDayDuration() {
        return dayDuration;
    }

    public void setDayDuration(int dayDuration) {
        this.dayDuration = dayDuration;
    }

    public int getNightDuration() {
        return nightDuration;
    }

    public void setNightDuration(int nightDuration) {
        this.nightDuration = nightDuration;
    }

    public double getUrbanDuration() {
        return urbanDuration;
    }

    public void setUrbanDuration(double urbanDuration) {
        this.urbanDuration = urbanDuration;
    }

    public double getHighWayDuration() {
        return highWayDuration;
    }

    public void setHighWayDuration(double highWayDuration) {
        this.highWayDuration = highWayDuration;
    }

    public double getOtherRoadDuration() {
        return otherRoadDuration;
    }

    public void setOtherRoadDuration(double otherRoadDuration) {
        this.otherRoadDuration = otherRoadDuration;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Long getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(Long idleTime) {
        this.idleTime = idleTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<StatisticOfHour> getTripOfHours() {
        return tripOfHours;
    }

    public void setTripOfHours(List<StatisticOfHour> tripOfHours) {
        this.tripOfHours = tripOfHours;
    }

    public Long getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(Long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public Long getMinIdleTime() {
        return minIdleTime;
    }

    public void setMinIdleTime(Long minIdleTime) {
        this.minIdleTime = minIdleTime;
    }

    public Long getMovementTime() {
        return movementTime;
    }

    public void setMovementTime(Long movementTime) {
        this.movementTime = movementTime;
    }

    public Long getMaxMovementTime() {
        return maxMovementTime;
    }

    public void setMaxMovementTime(Long maxMovementTime) {
        this.maxMovementTime = maxMovementTime;
    }

    public Long getMinMovementTime() {
        return minMovementTime;
    }

    public void setMinMovementTime(Long minMovementTime) {
        this.minMovementTime = minMovementTime;
    }

    public Long getTripCount() {
        return tripCount;
    }

    public void setTripCount(Long tripCount) {
        this.tripCount = tripCount;
    }

    public double getOverSpeedCount() {
        return overSpeedCount;
    }

    public void setOverSpeedCount(double overSpeedCount) {
        this.overSpeedCount = overSpeedCount;
    }

    public double getUnderLimitedSpeedDuration() {
        return underLimitedSpeedDuration;
    }

    public void setUnderLimitedSpeedDuration(double underLimitedSpeedDuration) {
        this.underLimitedSpeedDuration = underLimitedSpeedDuration;
    }

    public double getHighOverSpeedingDuration() {
        return highOverSpeedingDuration;
    }

    public void setHighOverSpeedingDuration(double highOverSpeedingDuration) {
        this.highOverSpeedingDuration = highOverSpeedingDuration;
    }

    public void setSumScore(double sumScore) {
        this.sumScore = sumScore;
    }

    public double getSumScore() {
        return sumScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public double getAverageScore() {
        return averageScore;
    }
}
