package com.taraan.dum.dto.statistic;

import java.util.Arrays;

public class StatisticDto {
    private long score;
    private long tripCount;
    private double accelerationEvent;//todo per 100 km
    private double brakingEvent;//todo per 100 km
    private double corneringEvent;//todo per 100 km
    private double averageSpeed;//todo how calculate
    private double maxSpeed;
    private double underLimitedSpeedingTime;//todo percentage
    private double underLimitedSpeedCount;//todo percentage
    private double overSpeedingTime;//todo percentage
    private double overSpeedCount;//todo percentage
    private double highOverSpeedingTime;//todo percentage
    private double highOverSpeedCount;//todo percentage
    private double dayPercentage;
    private double nightPercentage;
    private double urbanPercentage;
    private double highwayPercentage;
    private double otherPercentage;
    private StatisticOfHourDto[] statisticOfHourDtos;
    private double duration;//todo time duration
    private double distance;//
    private double idleTime;//
    private double averageDistance;

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getTripCount() {
        return tripCount;
    }

    public void setTripCount(long tripCount) {
        this.tripCount = tripCount;
    }

    public double getAccelerationEvent() {
        return accelerationEvent;
    }

    public void setAccelerationEvent(double accelerationEvent) {
        this.accelerationEvent = accelerationEvent;
    }

    public double getBrakingEvent() {
        return brakingEvent;
    }

    public void setBrakingEvent(double brakingEvent) {
        this.brakingEvent = brakingEvent;
    }

    public double getCorneringEvent() {
        return corneringEvent;
    }

    public void setCorneringEvent(double corneringEvent) {
        this.corneringEvent = corneringEvent;
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

    public double getOverSpeedingTime() {
        return overSpeedingTime;
    }

    public void setOverSpeedingTime(double overSpeedingTime) {
        this.overSpeedingTime = overSpeedingTime;
    }

    public double getOverSpeedCount() {
        return overSpeedCount;
    }

    public void setOverSpeedCount(double overSpeedCount) {
        this.overSpeedCount = overSpeedCount;
    }

    public double getDayPercentage() {
        return dayPercentage;
    }

    public void setDayPercentage(double dayPercentage) {
        this.dayPercentage = dayPercentage;
    }

    public double getNightPercentage() {
        return nightPercentage;
    }

    public void setNightPercentage(double nightPercentage) {
        this.nightPercentage = nightPercentage;
    }

    public double getUrbanPercentage() {
        return urbanPercentage;
    }

    public void setUrbanPercentage(double urbanPercentage) {
        this.urbanPercentage = urbanPercentage;
    }

    public double getHighwayPercentage() {
        return highwayPercentage;
    }

    public void setHighwayPercentage(double highwayPercentage) {
        this.highwayPercentage = highwayPercentage;
    }

    public double getOtherPercentage() {
        return otherPercentage;
    }

    public void setOtherPercentage(double otherPercentage) {
        this.otherPercentage = otherPercentage;
    }

    public StatisticOfHourDto[] getStatisticOfHourDtos() {
        return statisticOfHourDtos;
    }

    public void setStatisticOfHourDtos(StatisticOfHourDto[] statisticOfHourDtos) {
        this.statisticOfHourDtos = statisticOfHourDtos;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(double idleTime) {
        this.idleTime = idleTime;
    }

    public double getUnderLimitedSpeedingTime() {
        return underLimitedSpeedingTime;
    }

    public void setUnderLimitedSpeedingTime(double underLimitedSpeedingTime) {
        this.underLimitedSpeedingTime = underLimitedSpeedingTime;
    }

    public double getUnderLimitedSpeedCount() {
        return underLimitedSpeedCount;
    }

    public void setUnderLimitedSpeedCount(double underLimitedSpeedCount) {
        this.underLimitedSpeedCount = underLimitedSpeedCount;
    }

    public double getHighOverSpeedingTime() {
        return highOverSpeedingTime;
    }

    public void setHighOverSpeedingTime(double highOverSpeedingTime) {
        this.highOverSpeedingTime = highOverSpeedingTime;
    }

    public double getHighOverSpeedCount() {
        return highOverSpeedCount;
    }

    public void setHighOverSpeedCount(double highOverSpeedCount) {
        this.highOverSpeedCount = highOverSpeedCount;
    }

    @Override
    public String toString() {
        return "StatisticDto{" +
                "tripCount=" + tripCount +
                ", accelerationEvent=" + accelerationEvent +
                ", brakingEvent=" + brakingEvent +
                ", corneringEvent=" + corneringEvent +
                ", averageSpeed=" + averageSpeed +
                ", maxSpeed=" + maxSpeed +
                ", overSpeedingTime=" + overSpeedingTime +
                ", overSpeedCount=" + overSpeedCount +
                ", dayPercentage=" + dayPercentage +
                ", nightPercentage=" + nightPercentage +
                ", urbanPercentage=" + urbanPercentage +
                ", highwayPercentage=" + highwayPercentage +
                ", otherPercentage=" + otherPercentage +
                ", statisticOfHourDtos=" + Arrays.toString(statisticOfHourDtos) +
                ", duration=" + duration +
                ", distance=" + distance +
                ", idleTime=" + idleTime +
                '}';
    }

    public void setAverageDistance(double averageDistance) {
        this.averageDistance = averageDistance;
    }

    public double getAverageDistance() {
        return averageDistance;
    }
}
