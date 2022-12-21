package com.taraan.dum.dto.tracker;

public class TripTrackerInfoDto {
    private long order;
    private String deviceId;
    private double longitude;
    private double latitude;
    private double x;
    private double y;
    private double z;
    private double sumAcceleration;//todo remove
    private double realSpeed;
    private int direction;
    private String postDate;

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getRealSpeed() {
        return realSpeed;
    }

    public void setRealSpeed(double realSpeed) {
        this.realSpeed = realSpeed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public double getSumAcceleration() {
        return sumAcceleration;
    }

    public void setSumAcceleration(double sumAcceleration) {
        this.sumAcceleration = sumAcceleration;
    }

    @Override
    public String toString() {
        return "TripTrackerInfoDto{" +
                "order=" + order +
                ", deviceId='" + deviceId + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", realSpeed=" + realSpeed +
                ", direction=" + direction +
                ", postDate='" + postDate + '\'' +
                '}';
    }
}
