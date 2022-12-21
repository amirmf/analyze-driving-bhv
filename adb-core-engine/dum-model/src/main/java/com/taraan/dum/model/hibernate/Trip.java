package com.taraan.dum.model.hibernate;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Trip")
public class Trip implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_id_seq")
    @SequenceGenerator(sequenceName = "trip_id_seq", allocationSize = 1, name = "trip_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "tripStartDate")
    private Date tripStartDate;
    @Column(name = "tripEndDate")
    private Date tripEndDate;
    @ManyToOne
    private User user;
    @Column(name = "name")
    private String name;
    @Column(name = "speedUnderLimit")
    private double speedDistributionUnderLimit;
    @Column(name = "speedOverSpeed")
    private double speedDistributionOverSpeed;
    @Column(name = "speedHighOverSpeed")
    private double speedDistributionHighOverSpeed;
    @Column(name = "averageSpeed")
    private double averageSpeed;
    @Column(name = "maxSpeed")
    private double maxSpeed;
    @Column(name = "corneringCount")
    private int corneringCount;// formule +++++++++++++++
    @Column(name = "brakingCount")
    private int brakingCount;//-2
    @Column(name = "accelerationCount")
    private int accelerationCount;//+2
    @Column(name = "distance")
    private double distance;//format
    @Column(name = "otherDistance")
    private double otherDistance;
    @Column(name = "urbanDistance")
    private double urbanDistance;
    @Column(name = "highwayDistance")
    private double highwayDistance;
    @Column(name = "idleTime")
    private long idleTime;//speed = 0
    @Column(name = "duration")
    private long duration;//star trip - end trip
    @Column(name = "badDirection")
    private long badDirection;
    @Column(name = "minIdleTime")
    private long minIdleTime;
    @Column(name = "maxIdleTime")
    private long maxIdleTime;
    @Column(name = "movementTime")
    private long movementTime;
    @Column(name = "minMovementTime")
    private long minMovementTime;
    @Column(name = "maxMovementTime")
    private long maxMovementTime;
    @Column(name = "weekend")
    private boolean weekend;
    @Column(name = "deviceId")
    private String deviceId;
    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "TripId")
    @OrderBy("Id")
    private List<TripOfHour> tripOfHours = new ArrayList<>();
    @Column(name = "TripScore")
    private Long score;
    @Column(name = "isValid")
    private boolean valid = false;
    @Column(name = "isRemoved")
    private boolean removed = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTripStartDate() {

        return tripStartDate;
    }

    public void setTripStartDate(Date tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public Date getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(Date tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getSpeedDistributionUnderLimit() {
        return speedDistributionUnderLimit;
    }

    public void setSpeedDistributionUnderLimit(double speedDistributionUnderLimit) {
        this.speedDistributionUnderLimit = speedDistributionUnderLimit;
    }

    public double getSpeedDistributionOverSpeed() {
        return speedDistributionOverSpeed;
    }

    public void setSpeedDistributionOverSpeed(double speedDistributionOverSpeed) {
        this.speedDistributionOverSpeed = speedDistributionOverSpeed;
    }

    public double getSpeedDistributionHighOverSpeed() {
        return speedDistributionHighOverSpeed;
    }

    public void setSpeedDistributionHighOverSpeed(double speedDistributionHighOverSpeed) {
        this.speedDistributionHighOverSpeed = speedDistributionHighOverSpeed;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getCorneringCount() {
        return corneringCount;
    }

    public void setCorneringCount(int corneringCount) {
        this.corneringCount = corneringCount;
    }

    public int getBrakingCount() {
        return brakingCount;
    }

    public void setBrakingCount(int brakingCount) {
        this.brakingCount = brakingCount;
    }

    public int getAccelerationCount() {
        return accelerationCount;
    }

    public void setAccelerationCount(int accelerationCount) {
        this.accelerationCount = accelerationCount;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(long idleTime) {
        this.idleTime = idleTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getOtherDistance() {
        return otherDistance;
    }

    public void setOtherDistance(double otherDistance) {
        this.otherDistance = otherDistance;
    }

    public double getUrbanDistance() {
        return urbanDistance;
    }

    public void setUrbanDistance(double urbanDistance) {
        this.urbanDistance = urbanDistance;
    }

    public double getHighwayDistance() {
        return highwayDistance;
    }

    public void setHighwayDistance(double highwayDistance) {
        this.highwayDistance = highwayDistance;
    }

    public List<TripOfHour> getTripOfHours() {
        return tripOfHours;
    }

    public void setTripOfHours(List<TripOfHour> tripOfHours) {
        this.tripOfHours = tripOfHours;
    }

    public long getBadDirection() {
        return badDirection;
    }

    public void setBadDirection(long badDirection) {
        this.badDirection = badDirection;
    }

    public long getMinIdleTime() {
        return minIdleTime;
    }

    public void setMinIdleTime(long minIdleTime) {
        this.minIdleTime = minIdleTime;
    }

    public long getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public long getMovementTime() {
        return movementTime;
    }

    public void setMovementTime(long movementTime) {
        this.movementTime = movementTime;
    }

    public long getMinMovementTime() {
        return minMovementTime;
    }

    public void setMinMovementTime(long minMovementTime) {
        this.minMovementTime = minMovementTime;
    }

    public long getMaxMovementTime() {
        return maxMovementTime;
    }

    public void setMaxMovementTime(long maxMovementTime) {
        this.maxMovementTime = maxMovementTime;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getScore() {
        return score;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean isRemoved() {
        return removed;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", tripStartDate=" + tripStartDate +
                ", tripEndDate=" + tripEndDate +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", speedDistributionUnderLimit=" + speedDistributionUnderLimit +
                ", speedDistributionOverSpeed=" + speedDistributionOverSpeed +
                ", speedDistributionHighOverSpeed=" + speedDistributionHighOverSpeed +
                ", averageSpeed=" + averageSpeed +
                ", maxSpeed=" + maxSpeed +
                ", corneringCount=" + corneringCount +
                ", brakingCount=" + brakingCount +
                ", accelerationCount=" + accelerationCount +
                ", distance=" + distance +
                ", otherDistance=" + otherDistance +
                ", urbanDistance=" + urbanDistance +
                ", highwayDistance=" + highwayDistance +
                ", idleTime=" + idleTime +
                ", duration=" + duration +
                ", badDirection=" + badDirection +
                ", minIdleTime=" + minIdleTime +
                ", maxIdleTime=" + maxIdleTime +
                ", movementTime=" + movementTime +
                ", minMovementTime=" + minMovementTime +
                ", maxMovementTime=" + maxMovementTime +
                ", weekend=" + weekend +
                ", deviceId='" + deviceId + '\'' +
                ", tripOfHours=" + tripOfHours +
                ", score=" + score +
                ", valid=" + valid +
                '}';
    }

}
