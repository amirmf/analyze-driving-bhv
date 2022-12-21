package com.taraan.dum.dto.reminder;

public class ReminderDto {
    private Long id;
    private Long userId;
    private String phoneNumber;
    private Integer remindDayNotification;
    private String insuranceExpirationDate;
    private String insuranceRegisterOn;
    private String lastServiceDate;
    private Integer serviceAfter;
    private boolean active;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInsuranceRegisterOn() {
        return insuranceRegisterOn;
    }

    public void setInsuranceRegisterOn(String insuranceRegisterOn) {
        this.insuranceRegisterOn = insuranceRegisterOn;
    }

    public Integer getRemindDayNotification() {
        return remindDayNotification;
    }

    public void setRemindDayNotification(Integer remindDayNotification) {
        this.remindDayNotification = remindDayNotification;
    }

    public String getInsuranceExpirationDate() {
        return insuranceExpirationDate;
    }

    public void setInsuranceExpirationDate(String insuranceExpirationDate) {
        this.insuranceExpirationDate = insuranceExpirationDate;
    }

    public String getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public Integer getServiceAfter() {
        return serviceAfter;
    }

    public void setServiceAfter(Integer serviceAfter) {
        this.serviceAfter = serviceAfter;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ReminderDto{" +
                "remindDayNotification=" + remindDayNotification +
                ", insuranceExpirationDate='" + insuranceExpirationDate + '\'' +
                ", insuranceRegisterOn='" + insuranceRegisterOn + '\'' +
                ", lastServiceDate='" + lastServiceDate + '\'' +
                ", serviceAfter=" + serviceAfter +
                ", active=" + active +
                '}';
    }
}
