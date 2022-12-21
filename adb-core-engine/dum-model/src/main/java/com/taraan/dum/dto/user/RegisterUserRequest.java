package com.taraan.dum.dto.user;

public class RegisterUserRequest {
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
