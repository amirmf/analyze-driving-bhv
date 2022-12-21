package com.taraan.dum.dto.user;

public class RegisterUserResponse {
    private String code;

    public RegisterUserResponse(String code) {
        this.code = code;
    }

    public RegisterUserResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RegisterUserResponse{" +
                "code='" + code + '\'' +
                '}';
    }
}
