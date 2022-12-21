package com.taraan.dum.dto.reward;

public class RemoveBadgeRequiredRequest {
    private Long badge;
    private Long reward;

    public Long getBadge() {
        return badge;
    }

    public void setBadge(Long badge) {
        this.badge = badge;
    }

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }
}
