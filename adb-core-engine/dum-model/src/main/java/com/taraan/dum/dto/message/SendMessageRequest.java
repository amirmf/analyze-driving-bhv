package com.taraan.dum.dto.message;

/**
 * Created on 7/4/2018 10:07 PM.
 * User: Reza
 * Project : driving-usage-miner
 */
public class SendMessageRequest {
    private String text;
    private String phoneNumber;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
