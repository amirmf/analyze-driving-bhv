package com.taraan.dum.dto.trip;

import java.util.Date;

public class TripDto {
    private Long id;
    private String tripStartDate;
    private String tripEndDate;
    private String name;
    private double underLimitedCount;
    private double overSpeedCount;
    private double highOverSpeedCount;
    private double underLimitedPercentage;
    private double overSpeedCountPercentage;
    private double highOverSpeedCountPercentage;
    private double averageSpeed;
    private double maxSpeed;
    private int corneringCount;
    private int brakingCount;
    private int accelerationCount;
    private double distance;
    private double otherDistance;
    private double urbanDistance;
    private double highwayDistance;
    private long idleTime;
    private long duration;
    private long badDirection;
    private long minIdleTime;
    private long maxIdleTime;
    private long movementTime;
    private long minMovementTime;
    private long maxMovementTime;
    private int badBrakingCount;
    private int badAccelerationCount;
    private Long score;

    public double getUnderLimitedCount() {
        return underLimitedCount;
    }

    public void setUnderLimitedCount(double underLimitedCount) {
        this.underLimitedCount = underLimitedCount;
    }

    public double getUnderLimitedPercentage() {
        return underLimitedPercentage;
    }

    public void setUnderLimitedPercentage(double underLimitedPercentage) {
        this.underLimitedPercentage = underLimitedPercentage;
    }

    public double getOverSpeedCountPercentage() {
        return overSpeedCountPercentage;
    }

    public void setOverSpeedCountPercentage(double overSpeedCountPercentage) {
        this.overSpeedCountPercentage = overSpeedCountPercentage;
    }

    public double getHighOverSpeedCountPercentage() {
        return highOverSpeedCountPercentage;
    }

    public void setHighOverSpeedCountPercentage(double highOverSpeedCountPercentage) {
        this.highOverSpeedCountPercentage = highOverSpeedCountPercentage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public void setTripEndDate(String tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOverSpeedCount(double overSpeedCount) {
        this.overSpeedCount = overSpeedCount;
    }

    public double getOverSpeedCount() {
        return overSpeedCount;
    }

    public void setHighOverSpeedCount(double highOverSpeedCount) {
        this.highOverSpeedCount = highOverSpeedCount;
    }

    public double getHighOverSpeedCount() {
        return highOverSpeedCount;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getAverageSpeed() {
        return averageSpeed;
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

    public void setBadBrakingCount(int badBrakingCount) {
        this.badBrakingCount = badBrakingCount;
    }

    public int getBadBrakingCount() {
        return badBrakingCount;
    }

    public void setBadAccelerationCount(int badAccelerationCount) {
        this.badAccelerationCount = badAccelerationCount;
    }

    public int getBadAccelerationCount() {
        return badAccelerationCount;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "TripDto{" +
                "id=" + id +
                ", tripStartDate=" + tripStartDate +
                ", tripEndDate=" + tripEndDate +
                ", name='" + name + '\'' +
                ", overSpeedCount=" + overSpeedCount +
                ", highOverSpeedCount=" + highOverSpeedCount +
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
                ", badBrakingCount=" + badBrakingCount +
                ", badAccelerationCount=" + badAccelerationCount +
                ", score=" + score +
                '}';
    }
}
