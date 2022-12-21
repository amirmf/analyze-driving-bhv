package com.taraan.dum.dto.trip;

public class EndTripDto {
    private String name;
    private Long userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EndTripDto{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
