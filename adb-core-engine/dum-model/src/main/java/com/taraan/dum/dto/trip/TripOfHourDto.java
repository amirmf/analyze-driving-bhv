package com.taraan.dum.dto.trip;

public class TripOfHourDto {
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
        return "TripOfHourDto{" +
                "hour=" + hour +
                ", distance=" + distance +
                ", time=" + time +
                '}';
    }
}
