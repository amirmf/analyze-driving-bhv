package com.taraan.dum.dto.statistic;

public class StatisticOfHourDto {
    private int hour;
    private double distance;
    private double time;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "StatisticOfHourDto{" +
                "hour=" + hour +
                ", distance=" + distance +
                ", time=" + time +
                '}';
    }
}
