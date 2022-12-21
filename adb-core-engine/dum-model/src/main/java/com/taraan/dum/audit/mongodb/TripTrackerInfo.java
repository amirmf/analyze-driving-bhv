package com.taraan.dum.audit.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "TripTrackerInfo")
public class TripTrackerInfo {
    @Id
    private String id;
    private long order;
    private String deviceId;
    private double longitude;
    private double latitude;
    private double x;
    private double y;
    private double z;
    private double sumAcceleration;
    private double realSpeed;
    private int direction;
    private Date postDate;
    private RoadType roadType;
    private Date userPostDate;
    private long postDateTimeStamp;
    private boolean isWalking;
    private long userPostDateTimeStamp;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getRealSpeed() {
        return realSpeed;
    }

    public void setRealSpeed(double realSpeed) {
        this.realSpeed = realSpeed;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public RoadType getRoadType() {
        return roadType;
    }

    public void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }



    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public void setUserPostDate(Date userPostDate) {
        this.userPostDate = userPostDate;
    }

    public Date getUserPostDate() {
        return userPostDate;
    }

    public void setPostDateTimeStamp(long postDateTimeStamp) {
        this.postDateTimeStamp = postDateTimeStamp;
    }

    public long getPostDateTimeStamp() {
        return postDateTimeStamp;
    }

    public double getSumAcceleration() {
        return sumAcceleration;
    }

    public void setSumAcceleration(double sumAcceleration) {
        this.sumAcceleration = sumAcceleration;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public void setUserPostDateTimeStamp(long userPostDateTimeStamp) {
        this.userPostDateTimeStamp = userPostDateTimeStamp;
    }

    public long getUserPostDateTimeStamp() {
        return userPostDateTimeStamp;
    }
}
