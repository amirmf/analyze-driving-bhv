package com.taraan.dum.dto.trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripInfoDto {
    private double speedDistributionUnderLimit;
    private double speedDistributionOverSpeed;
    private double speedDistributionHighOverSpeed;
    private double averageSpeed;
    private double maxSpeed;
    private int corneringCount;// formule +++++++++++++++
    private int brakingCount;//-2
    private int accelerationCount;//+2
    private double distance;//format
    private double otherDistance;
    private double urbanDistance;
    private double highwayDistance;
    private long idleTime;//speed = 0
    private long duration;//star trip - end trip
    private long badDirection;
    private long minIdleTime;
    private long maxIdleTime;
    private long movementTime;
    private long minMovementTime;
    private long maxMovementTime;
    private TripOfHourDto[] tripOfHours;
    private boolean weekend;

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

    public TripOfHourDto[] getTripOfHours() {
        return tripOfHours;
    }

    public void setTripOfHours(TripOfHourDto[] tripOfHours) {
        this.tripOfHours = tripOfHours;
    }

    @Override
    public String toString() {
        return "TripInfoDto{" +
                "speedDistributionUnderLimit=" + speedDistributionUnderLimit +
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
                ", tripOfHours=" + Arrays.toString(tripOfHours) +
                '}';
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }

    public boolean isWeekend() {
        return weekend;
    }
}
