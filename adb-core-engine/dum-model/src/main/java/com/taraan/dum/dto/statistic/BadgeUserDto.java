package com.taraan.dum.dto.statistic;

public class BadgeUserDto {
    private Long count;
    private Long badge;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getBadge() {
        return badge;
    }

    public void setBadge(Long badge) {
        this.badge = badge;
    }

    public BadgeUserDto(Long count, Long badge) {
        this.count = count;
        this.badge = badge;
    }

    public BadgeUserDto() {
    }

    @Override
    public String toString() {
        return "BadgeUserDto{" +
                "count=" + count +
                ", badge=" + badge +
                '}';
    }
}
