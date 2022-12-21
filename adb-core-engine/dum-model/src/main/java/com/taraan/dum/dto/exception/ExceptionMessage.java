package com.taraan.dum.dto.exception;

public class ExceptionMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExceptionMessage(String message) {
        this.message = message;
    }
}
